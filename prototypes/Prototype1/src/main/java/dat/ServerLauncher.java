package dat;

import dat.config.ApplicationConfig;

public class ServerLauncher {

    public static void main(String[] args) {
        ApplicationConfig.startServer(7070);
    }
}