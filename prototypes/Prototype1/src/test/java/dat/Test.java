package dat;

import dat.config.ApplicationConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class Test {

    @BeforeAll
    public void beforeAll() {
        ApplicationConfig.startServer(7777);
    }

    @AfterAll
    public void afterAll() {
        ApplicationConfig.stopServer();
    }
}