package com.meysam.users.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "com.meysam.users.*"
        ,"com.meysam.common.utils"
        ,"com.meysam.common.security"})
@EnableJpaRepositories(basePackages = "com.meysam.common.dao")
@EntityScan(basePackages = "com.meysam.common.model.entity")
@EnableHystrix
@EnableFeignClients(basePackages = {"com.meysam.users.service.*","com.meysam.common.service.*"})
public class UsersWebApiApplication {

    public static void main(String []args){
        SpringApplication.run(UsersWebApiApplication.class);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
