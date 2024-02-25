package dat;

import dat.entities.SubMedia;
import dat.entities.User;

import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        setup();
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("1. Create post");
            String input = SCANNER.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Enter sub media name:");
                    String subMediaName = SCANNER.nextLine();
                    System.out.println("Enter user name:");
                    String userName = SCANNER.nextLine();
                    System.out.println("Enter title:");
                    String title = SCANNER.nextLine();
                    System.out.println("Enter content:");
                    String content = SCANNER.nextLine();
                    SubMedia subMedia = new SubMedia(subMediaName);
                    User user = new User(userName);
                    long id = subMedia.createPost(user, System.currentTimeMillis(), title, content);
                    if (id == 0) {
                        System.out.println("Post creation failed");
                    } else {
                        System.out.println("Post created with id " + id);
                    }
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private static void setup() {
        SubMedia subMedia1 = new SubMedia("nature");
        SubMedia subMedia2 = new SubMedia("cars");
        User user1 = new User("user1");
        User user2 = new User("user2");
    }
}