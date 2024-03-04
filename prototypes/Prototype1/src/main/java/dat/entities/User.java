package dat.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class User {

    private String username;
    private Set<Post> posts = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private Set<PostRating> postRatings = new HashSet<>();
    private Set<CommentRating> commentRatings = new HashSet<>();
    private Set<Role> roles = new HashSet<>();

    public User(String username) {
        this.username = username;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }
}