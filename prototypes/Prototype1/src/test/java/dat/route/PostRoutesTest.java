package dat.route;

import dat.config.ApplicationConfig;
import dat.dao.PostDAO;
import dat.dao.SubMediaDAO;
import dat.dao.UserDAO;
import dat.dto.PostDTO;
import dat.entities.Post;
import dat.entities.SubMedia;
import dat.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostRoutesTest extends dat.Test {

    @BeforeEach
    public void beforeEach() {
        PostDAO postDAO = PostDAO.getInstance();
        postDAO.truncate();

        UserDAO userDAO = UserDAO.getInstance();
        userDAO.truncate();

        userDAO.create(new User("test"));
        userDAO.create(new User("test2"));

        SubMediaDAO subMediaDAO = SubMediaDAO.getInstance();
        subMediaDAO.truncate();

        subMediaDAO.create(new SubMedia("nature"));
    }

    @Test
    public void testCreatePost() {
        PostDTO postDTO = new PostDTO("test", "nature", "title", "content");
        Long id = given()
                .body(postDTO)
                .when()
                .post(ApplicationConfig.getLocalHostURL() + "/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Long.class);

        assertEquals(1, id);
    }

    @Test
    public void testGetPost() {
        PostDTO postDTO = new PostDTO("test", "nature", "title", "content");
        Long id = given()
                .body(postDTO)
                .when()
                .post(ApplicationConfig.getLocalHostURL() + "/posts")
                .then()
                .statusCode(201)
                .extract()
                .as(Long.class);

        assertEquals(1, id);
        PostDTO post = given()
                .when()
                .get(ApplicationConfig.getLocalHostURL() + "/posts/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(PostDTO.class);

        assertEquals("title", post.getTitle());
    }
}