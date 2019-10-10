package com.easy.eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HystrixEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixEurekaServerApplication.class, args);
    }
}