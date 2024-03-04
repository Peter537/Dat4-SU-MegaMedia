package dat.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class SubMedia {

    private String name;
    private final Set<Post> posts = new HashSet<>();

    public SubMedia(String name) {
        this.name = name;
    }

    public long createPost(User user, long timestamp, String title, String content) {
        if (!validateRequirements(user))
            return 0;
        if (!validatePost(title, content))
            return 0;
        if (!Validation.validateGlobalPostingRequirements(user))
            return 0;

        Post post = new Post(user, this, timestamp, title, content);
        posts.add(post);
        user.getPosts().add(post);
        return post.getId();
    }

    /*
     * TODO: This would check requirements from Sub Media to see if user can post
     */
    public boolean validateRequirements(User user) {
        return true;
    }

    /*
     * TODO: This would validate title and content according to Sub Media rules
     */
    public boolean validatePost(String title, String content) {
        return title != null && !title.isEmpty() && content != null && !content.isEmpty();
    }

    public Post getPost(long id) {
        for (Post post : posts) {
            if (post.getId() == id)
                return post;
        }

        return null;
    }
}