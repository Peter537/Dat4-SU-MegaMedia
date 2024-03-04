package dat.route;

import dat.controller.UserController;
import dat.dao.UserDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRoutes implements Route {

    private final UserController userController;

    public UserRoutes() {
        UserDAO userDAO = UserDAO.getInstance();
        this.userController = new UserController(userDAO);
    }

    @Override
    public String getBasePath() {
        return "/users";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            get("/{username}", userController::getUser);
        };
    }
}