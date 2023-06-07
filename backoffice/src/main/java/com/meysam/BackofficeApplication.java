package com.meysam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BackofficeApplication {

    public static void main(String []args){
        SpringApplication.run(BackofficeApplication.class,args);
    }

}
