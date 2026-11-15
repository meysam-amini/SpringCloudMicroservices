package com.meysam.outbox.outboxengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {
        "com.meysam"
})
@EnableJpaRepositories(basePackages = {
        "com.meysam"

})
@EntityScan(basePackages = {
        "com.meysam"
})
@EnableScheduling
@SpringBootApplication
public class OutboxEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutboxEngineApplication.class, args);
    }

}
