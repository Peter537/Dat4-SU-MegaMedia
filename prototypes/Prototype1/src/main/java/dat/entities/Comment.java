package dat.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Comment {

    @Setter
    private static long idCounter = 1;

    private final long id;
    private User user;
    private Post post;
    private long timestamp;
    private String content;
    private Set<CommentRating> ratings = new HashSet<>();

    public Comment(User user, Post post, long timestamp, String content) {
        this.id = idCounter++;
        this.user = user;
        this.post = post;
        this.timestamp = timestamp;
        this.content = content;
    }

    public boolean like(User user) {
        for (CommentRating rating : ratings) {
            if (rating.getUser().equals(user)) {
                if (rating.getRating() == 1)
                    return false;
                ratings.remove(rating);
                break;
            }
        }

        CommentRating rating = new CommentRating(user, this, 1);
        ratings.add(rating);
        return true;
    }

    public boolean dislike(User user) {
        for (CommentRating rating : ratings) {
            if (rating.getUser().equals(user)) {
                if (rating.getRating() == -1)
                    return false;
                ratings.remove(rating);
                break;
            }
        }

        CommentRating rating = new CommentRating(user, this, -1);
        ratings.add(rating);
        return true;
    }

    public void neutral(User user) {
        for (CommentRating rating : ratings) {
            if (rating.getUser().equals(user)) {
                ratings.remove(rating);
                break;
            }
        }
    }
}