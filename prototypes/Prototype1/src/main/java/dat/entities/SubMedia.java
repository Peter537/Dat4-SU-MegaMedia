package dat.entities;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class SubMedia {

    private final String name;
    private final Set<Post> posts = new HashSet<>();

    public SubMedia(String name) {
        this.name = name;
    }

    public long createPost(User user, long timestamp, String title, String content) {
        Post post = new Post(user, this, timestamp, title, content);
        posts.add(post);
        user.getPosts().add(post);
        return post.getId();
    }

    public Post getPost(long id) {
        for (Post post : posts) {
            if (post.getId() == id)
                return post;
        }

        return null;
    }
}