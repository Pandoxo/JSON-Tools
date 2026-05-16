package pl.put.poznan.tools.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the JSON Tools application.
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.tools.rest"})
public class JsonToolsApplication {

    /**
     * The main method that launches the Spring Boot application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(JsonToolsApplication.class, args);
    }
}
