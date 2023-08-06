package com.meysam.auth.webapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.meysam.auth.*"
        ,"com.meysam.common.service.*"
        ,"com.meysam.common.utils.*"
        ,"com.meysam.common.configs.*"
        ,"com.meysam.users.service.*"})
@EnableJpaRepositories(basePackages = "com.meysam.common.dao")
@EntityScan(basePackages = "com.meysam.common.model.entity")
@EnableFeignClients(basePackages = {"com.meysam.users.service.*","com.meysam.common.service.*"})
public class AuthWebApiApplication {

    public static void main(String []args){
        SpringApplication.run(AuthWebApiApplication.class);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("SpringDoc example")
                .description("SpringDoc application")
                .version("v0.0.1"));
    }

}
