package com.meysam.backoffice.webapi;

import com.meysam.backoffice.service.cached.api.CachedDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
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
@ComponentScan(basePackages = "com.meysam")
@EnableJpaRepositories(basePackages = "com.meysam")
@EntityScan(basePackages = "com.meysam")
@EnableFeignClients(basePackages = "com.meysam")
@Slf4j
@RequiredArgsConstructor
public class BackofficeWebApiApplication implements CommandLineRunner {

    private final CachedDataService cachedDataService;

    public static void main(String []args){
        SpringApplication.run(BackofficeWebApiApplication.class,args);

    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void run(String... args) throws Exception {
        cachedDataService.refreshCache();
    }
}
