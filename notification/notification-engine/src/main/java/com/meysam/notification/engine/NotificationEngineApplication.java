package com.meysam.notification.engine;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.meysam.common.configs.*",
        "com.meysam.notification.engine.*"
})

@SpringBootApplication
public class NotificationEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationEngineApplication.class, args);
    }

}
