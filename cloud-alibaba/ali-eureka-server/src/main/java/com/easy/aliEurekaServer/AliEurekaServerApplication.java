package com.easy.aliEurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AliEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliEurekaServerApplication.class, args);
    }
}