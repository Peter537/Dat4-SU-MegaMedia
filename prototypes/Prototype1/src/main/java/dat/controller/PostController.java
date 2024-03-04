package dat.controller;

import dat.dao.PostDAO;
import dat.dto.PostDTO;
import dat.entities.Post;
import io.javalin.http.Context;

public class PostController {

    private final PostDAO postDAO;

    public PostController(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public void createPost(Context ctx) {
        PostDTO postDTO = ctx.bodyAsClass(PostDTO.class);
        Long id = postDAO.create(postDTO);
        ctx.json(id);
        ctx.status(201);
    }

    public void getPost(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        Post post = postDAO.read(id);
        ctx.json(new PostDTO(post));
    }
}