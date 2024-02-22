package dat.entities;

public class Validation {

    /*
     * TODO: This would be automatically validated from special rules, ex. laws, company policies, etc.
     */
    public static boolean validatePost(Post post) {
        return true;
    }

    /*
     * TODO: This would automatically validate if user has posting rights globally
     */
    public static boolean validateGlobalPostingRequirements(User user) {
        return true;
    }
}