package com.meysam.backoffice.webapi;

import lombok.extern.slf4j.Slf4j;
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
@ComponentScan(basePackages = {
        "com.meysam.backoffice.*",
        "com.meysam.backoffice.service.permission.*"
//        "com.meysam.users.*"
        ,"com.meysam.common.utils.*"
        ,"com.meysam.common.configs.*"
//        ,"com.meysam.common.security"
        ,"com.meysam.common.service.*"
})
@EnableJpaRepositories(basePackages = {
        "com.meysam.common.dao",
        "com.meysam.common.customsecurity.repository"

}

)
@EntityScan(basePackages = "com.meysam.common.model.entity")
@EnableFeignClients(basePackages = {"com.meysam.common.service.*"})
@Slf4j
public class BackofficeWebApiApplication {

    public static void main(String []args){
        SpringApplication.run(BackofficeWebApiApplication.class,args);
        log.info("first run backoffice");
        log.info("######################################################################");
        log.error("######################################################################");
        log.info("######################################################################");
        log.info("######################################################################");
        log.error("######################################################################");
        log.error("first run backoffice");
        log.info("######################################################################");
        log.info("######################################################################");
        log.error("######################################################################");
        log.info("######################################################################");
        log.info("######################################################################");
        log.error("######################################################################");
        log.info("first run backoffice");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
