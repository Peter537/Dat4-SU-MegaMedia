package dat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentRating {

    private User user;
    private Comment comment;
    private int rating;
}