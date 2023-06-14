package com.meysam.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.meysam.common.*")
public class AuthWebApiApplication {

    public static void main(String []args){
        SpringApplication.run(AuthWebApiApplication.class);
    }
}
