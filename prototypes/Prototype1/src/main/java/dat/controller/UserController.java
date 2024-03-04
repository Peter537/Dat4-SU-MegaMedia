package dat.controller;

import dat.dao.UserDAO;
import dat.entities.User;
import io.javalin.http.Context;

public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void getUser(Context ctx) {
        // TODO
        ctx.status(200);
        ctx.json(userDAO.read(ctx.pathParam("username")));
    }
}