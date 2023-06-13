package com.meysam.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthWebApiApplication {

    public static void main(String []args){
        SpringApplication.run(AuthWebApiApplication.class);
    }
}
