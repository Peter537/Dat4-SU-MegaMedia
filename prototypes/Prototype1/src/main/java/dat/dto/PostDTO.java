package dat.dto;

import dat.entities.Post;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {

    private Long id;
    private String username;
    private String subMedia;

    private String title;
    private String content;
    private Long timestamp;

    public PostDTO(String username, String subMedia, String title, String content) {
        this.username = username;
        this.subMedia = subMedia;
        this.title = title;
        this.content = content;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.subMedia = post.getSubMedia().getName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.timestamp = post.getTimestamp();
    }
}