package me.sathish.ai_project.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("me.sathish.ai_project")
public class AiProjectApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AiProjectApplication.class, args);
    }

}
