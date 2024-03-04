package dat.route;

import dat.config.ApplicationConfig;
import dat.dao.UserDAO;
import dat.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRoutesTest extends dat.Test {

    @BeforeEach
    public void beforeEach() {
        UserDAO userDAO = UserDAO.getInstance();
        userDAO.truncate();

        userDAO.create(new User("test"));
        userDAO.create(new User("test2"));
    }

    @Test
    public void testGetUser() {
        User user = given()
                .when()
                .get(ApplicationConfig.getLocalHostURL() + "/users/test")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        assertEquals("test", user.getUsername());
    }
}