package dat.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Setter
    private static long idCounter = 1;

    private long id;
    private User user;
    private SubMedia subMedia;
    private long timestamp;
    private String title;
    private String content;
    private Set<Comment> comments = new HashSet<>();
    private Set<PostRating> ratings = new HashSet<>();
    private PostStatus status;
    private long views = 0;

    public Post(User user, SubMedia subMedia, long timestamp, String title, String content) {
        this.id = idCounter++;
        this.user = user;
        this.subMedia = subMedia;
        this.timestamp = timestamp;
        this.title = title;
        this.content = content;
        this.status = PostStatus.PENDING;
    }

    public Post(User user, SubMedia subMedia, String title, String content) {
        this(user, subMedia, System.currentTimeMillis(), title, content);
    }

    public void createComment(User user, long timestamp, String content) {
        Comment comment = new Comment(user, this, timestamp, content);
        comments.add(comment);
    }

    public boolean like(User user) {
        for (PostRating rating : ratings) {
            if (rating.getUser().equals(user)) {
                if (rating.getRating() == 1)
                    return false;
                ratings.remove(rating);
                break;
            }
        }

        PostRating rating = new PostRating(user, this, 1);
        ratings.add(rating);
        return true;
    }

    public boolean dislike(User user) {
        for (PostRating rating : ratings) {
            if (rating.getUser().equals(user)) {
                if (rating.getRating() == -1)
                    return false;
                ratings.remove(rating);
                break;
            }
        }

        PostRating rating = new PostRating(user, this, -1);
        ratings.add(rating);
        return true;
    }

    public void neutral(User user) {
        for (PostRating rating : ratings) {
            if (rating.getUser().equals(user)) {
                ratings.remove(rating);
                break;
            }
        }
    }

    public void addView() {
        views++;
    }
}