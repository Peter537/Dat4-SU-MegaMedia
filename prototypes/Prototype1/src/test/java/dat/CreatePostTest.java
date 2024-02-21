package dat;

import dat.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePostTest {

    @BeforeEach
    void beforeEach() {
        // in real program this wouldn't be necessary
        Post.setIdCounter(1);
        Comment.setIdCounter(1);
    }

    @Test
    void testCreatePostSuccess() {
        SubMedia subMedia = new SubMedia("nature");
        User user1 = new User("user1");
        long id = subMedia.createPost(user1, System.currentTimeMillis(), "title", "content");
        assertEquals(1, id);
    }

    @Test
    void testGetPostSuccess() {
        SubMedia subMedia = new SubMedia("nature");
        User user1 = new User("user1");
        long id = subMedia.createPost(user1, System.currentTimeMillis(), "title", "content");
        assertEquals(1, id);
        assertEquals("title", subMedia.getPost(1).getTitle());
    }
}