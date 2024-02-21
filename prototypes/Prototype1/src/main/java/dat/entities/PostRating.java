package dat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostRating {

    private User user;
    private Post post;
    private int rating;
}