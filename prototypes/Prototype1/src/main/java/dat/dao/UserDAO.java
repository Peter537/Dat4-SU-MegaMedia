package dat.dao;

import dat.entities.User;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {

    private static UserDAO INSTANCE;
    private static Map<String, User> users = new HashMap<>();

    private UserDAO() {}

    public static UserDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDAO();
        }

        return INSTANCE;
    }

    public void truncate() {
        users.clear();
    }

    public void create(User user) {
        users.put(user.getUsername(), user);
    }

    public User read(String username) {
        return users.getOrDefault(username, null);
    }
}