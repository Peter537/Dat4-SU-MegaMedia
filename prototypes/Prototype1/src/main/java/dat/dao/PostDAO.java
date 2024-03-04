package dat.dao;

import dat.dto.PostDTO;
import dat.entities.Post;
import dat.entities.SubMedia;
import dat.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDAO {

    private static PostDAO INSTANCE;
    private static Map<Long, Post> posts = new HashMap<>();

    private PostDAO() {}

    public static PostDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostDAO();
        }

        return INSTANCE;
    }

    public void truncate() {
        posts.clear();
    }

    public Long create(PostDTO postDTO) {
        User user = UserDAO.getInstance().read(postDTO.getUsername());
        if (user == null) {
            user = new User(postDTO.getUsername());
            UserDAO.getInstance().create(user);
        }
        SubMedia subMedia = SubMediaDAO.getInstance().read(postDTO.getSubMedia());
        if (subMedia == null) {
            subMedia = new SubMedia(postDTO.getSubMedia());
            SubMediaDAO.getInstance().create(subMedia);
        }
        Post post = new Post(user, subMedia, postDTO.getTitle(), postDTO.getContent());
        this.create(post);
        return post.getId();
    }

    public Long create(Post post) {
        posts.put(post.getId(), post);
        return post.getId();
    }

    public Post read(long id) {
        return posts.getOrDefault(id, null);
    }

    public List<Post> readAll() {
        return List.copyOf(posts.values());
    }
}