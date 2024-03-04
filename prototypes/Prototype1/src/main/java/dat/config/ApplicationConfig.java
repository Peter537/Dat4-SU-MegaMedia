package dat.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import dat.exception.ApiException;
import dat.message.Message;
import dat.message.ValidationMessage;
import dat.route.PostRoutes;
import dat.route.Route;
import dat.route.UserRoutes;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import io.javalin.validation.ValidationError;
import io.javalin.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);
    private static final String CONTENT_TYPE = ContentType.JSON;
    private static final String CONTEXT_PATH = "/api/v1";
    private static final String ROUTE_OVERVIEW_PATH = "/routes";

    private static Javalin app;
    private static long requestCount = 1;

    private static void createJavalinApp() {
        app = Javalin.create(config -> {
            config.plugins.enableDevLogging(); // enables extensive development logging in terminal
            config.http.defaultContentType = CONTENT_TYPE; // default content type for requests
            config.routing.contextPath = CONTEXT_PATH; // base path for all routes
            config.plugins.register(new RouteOverviewPlugin(ROUTE_OVERVIEW_PATH)); // html overview of all registered
            // routes at "/" for api
            // documentation:
            // https://javalin.io/news/2019/08/11/javalin-3.4.1-released.html
        });
        setExceptionHandling();
        setBeforeHandling();
        setAfterHandling();
        addRoutes(
                new UserRoutes(),
                new PostRoutes()
                // add routes here
        );
    }

    private static void setExceptionHandling() {
        ExceptionManagerHandler em = new ExceptionManagerHandler();
        app.exception(ApiException.class, em::apiException);
        app.exception(Exception.class, em::exception);
        app.exception(ValidationException.class, em::validationException);
        app.exception(UnrecognizedPropertyException.class, em::unrecognizedPropertyException);
        app.exception(InvalidFormatException.class, em::invalidFormatException);
    }

    private static void setBeforeHandling() {
        app.before(ctx -> {
            long count = requestCount++;
            ctx.attribute("requestCount", count);
            ctx.attribute("requestInfo", ctx.req().getMethod() + " " + ctx.req().getRequestURI());
            LOGGER.info("Request {} - {} was received", count, ctx.attribute("requestInfo"));
        });
    }

    private static void setAfterHandling() {
        app.after(ctx -> LOGGER.info("Request {} - {} was handled with status code {}",
                ctx.attribute("requestCount"),
                ctx.attribute("requestInfo"),
                ctx.status()));
    }

    private static void addRoutes(Route... routes) {
        app.routes(() -> Arrays.stream(routes).forEach(route -> path(route.getBasePath(), route.getRoutes())));
    }

    public static void startServer(int port) {
        if (app == null) {
            createJavalinApp();
        }

        app.start(port);
    }

    public static void stopServer() {
        if (app == null) {
            throw new IllegalStateException("Server is not running");
        }

        app.stop();
    }

    public static String getLocalHostURL() {
        return "http://localhost:" + app.port() + CONTEXT_PATH;
    }

    private static class ExceptionManagerHandler {

        private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionManagerHandler.class);

        public void apiException(ApiException e, Context ctx) {
            ctx.status(e.getStatusCode());
            ctx.json(new Message(e.getStatusCode(), System.currentTimeMillis(), e.getMessage()));
            this.logException(e, ctx);
        }

        public void exception(Exception e, Context ctx) {
            ctx.status(500);
            ctx.json(new Message(500, System.currentTimeMillis(), e.getMessage()));
            this.logException(e, ctx);
        }

        public void validationException(ValidationException e, Context ctx) {
            Map<String, List<ValidationError<Object>>> errors = e.getErrors();
            List<ValidationError<Object>> errorList = new ArrayList<>();
            int statusCode = 0;
            if (errors.containsKey("REQUEST_BODY")) {
                errorList.addAll(errors.get("REQUEST_BODY"));
                statusCode = 400;
            } else if (errors.containsKey("id")) {
                errorList.addAll(errors.get("id"));
                statusCode = 404;
            }

            ValidationError<Object> error = errorList.isEmpty() ? null : errorList.get(0);
            if (error != null) {
                ctx.status(statusCode);
                ctx.json(new ValidationMessage(System.currentTimeMillis(), error.getMessage(), error.getArgs(),
                        error.getValue()));
            } else {
                ctx.status(500);
                ctx.json(new Message(500, System.currentTimeMillis(), e.getMessage()));
            }

            this.logException(e, ctx);
        }

        public void unrecognizedPropertyException(UnrecognizedPropertyException e, Context ctx) {
            ctx.status(400);
            ctx.json(new Message(400, System.currentTimeMillis(), e.getMessage()));
            this.logException(e, ctx);
        }

        public void invalidFormatException(InvalidFormatException e, Context ctx) {
            ctx.status(400);
            ctx.json(new Message(400, System.currentTimeMillis(), e.getMessage()));
            this.logException(e, ctx);
        }

        private void logException(Exception e, Context ctx) {
            LOGGER.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
            LOGGER.error("Clients IP: " + ctx.ip());
            LOGGER.error("Exception: ", e);
        }
    }
}