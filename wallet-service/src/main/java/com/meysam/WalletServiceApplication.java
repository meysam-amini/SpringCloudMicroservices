package com.meysam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import util.LocaleMessageSourceService;

@SpringBootApplication
@EnableDiscoveryClient
public class WalletServiceApplication {

    public static void main(String []args){
        SpringApplication.run(WalletServiceApplication.class,args);
    }

    @Bean
    public LocaleMessageSourceService getLocaleMessageSourceService(){
        return new LocaleMessageSourceService();
    }
}
