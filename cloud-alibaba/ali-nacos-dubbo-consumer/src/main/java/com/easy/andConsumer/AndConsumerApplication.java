package com.easy.andConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AndConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AndConsumerApplication.class, args);
    }
}