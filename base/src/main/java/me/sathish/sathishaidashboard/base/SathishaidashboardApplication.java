package me.sathish.sathishaidashboard.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("me.sathish.sathishaidashboard")
public class SathishaidashboardApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SathishaidashboardApplication.class, args);
    }

}
