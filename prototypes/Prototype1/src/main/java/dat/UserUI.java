package dat;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dto.PostDTO;
import dat.http.HttpClientUtil;

import java.util.Date;
import java.util.Scanner;

public class UserUI {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void run(String username) throws Exception {
        System.out.println("Hello, I'm ClientOne");
        while (true) {
            System.out.println("Options:");
            System.out.println(" 1. Create post in sub media");
            System.out.println(" 2. View post by id");

            String input = SCANNER.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Enter sub media name:");
                    String subMediaName = SCANNER.nextLine();
                    System.out.println("Enter title:");
                    String title = SCANNER.nextLine();
                    System.out.println("Enter content:");
                    String content = SCANNER.nextLine();
                    System.out.println("Post created");
                    PostDTO postDTO = new PostDTO(username, subMediaName, title, content);
                    String response = HttpClientUtil.sendPostRequest("posts", OBJECT_MAPPER.writeValueAsString(postDTO));
                    System.out.println("Post created with id: " + response);
                    break;
                case "2":
                    System.out.println("Enter post id:");
                    String postId = SCANNER.nextLine();
                    String response2 = HttpClientUtil.sendGetRequest("posts/" + postId);
                    PostDTO postDTO2 = OBJECT_MAPPER.readValue(response2, PostDTO.class);
                    System.out.println("Viewing post:");
                    System.out.println(" id: " + postDTO2.getId());
                    System.out.println(" username: " + postDTO2.getUsername());
                    System.out.println(" sub media: " + postDTO2.getSubMedia());
                    System.out.println(" title: " + postDTO2.getTitle());
                    System.out.println(" content: " + postDTO2.getContent());
                    System.out.println(" timestamp: " + new Date(postDTO2.getTimestamp()));
                    break;
                default:
                    System.out.println("Invalid input");
            }
            System.out.println();
        }
    }
}