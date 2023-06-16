package com.meysam.auth;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.meysam.auth.*.*","com.meysam.auth.service.*","com.meysam.common.dao","com.meysam.common.*"})
public class AuthWebApiApplication implements CommandLineRunner {

//    private final GeneralPropertiesService generalPropertiesService;
//
//    public AuthWebApiApplication(GeneralPropertiesService generalPropertiesService) {
//        this.generalPropertiesService = generalPropertiesService;
//    }

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

    @Override
    public void run(String... args) throws Exception {
        // In the case of using H2 database:
        insertDefaultGeneralProperties();
    }

    private void insertDefaultGeneralProperties(){
//        generalPropertiesService.addSettings(DefaultConstants.defaultConstants);
    }
}
