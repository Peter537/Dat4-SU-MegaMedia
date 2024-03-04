package dat.route;

import dat.controller.PostController;
import dat.dao.PostDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PostRoutes implements Route{

    private final PostController postController;

    public PostRoutes() {
        PostDAO postDAO = PostDAO.getInstance();
        this.postController = new PostController(postDAO);
    }

    @Override
    public String getBasePath() {
        return "/posts";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            post("/", postController::createPost);
            get("/{id}", postController::getPost);
        };
    }
}