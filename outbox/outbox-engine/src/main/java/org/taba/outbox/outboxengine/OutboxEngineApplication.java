package org.taba.outbox.outboxengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {
        "org.taba.*.*"
})
@EnableJpaRepositories(basePackages = {
        "org.taba.*.*"

})
@EntityScan(basePackages = {
        "org.taba.*.*"
})
@EnableScheduling
@SpringBootApplication
public class OutboxEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutboxEngineApplication.class, args);
    }

}
