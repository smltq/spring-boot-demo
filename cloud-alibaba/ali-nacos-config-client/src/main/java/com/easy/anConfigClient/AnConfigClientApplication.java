package com.easy.anConfigClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AnConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnConfigClientApplication.class, args);
    }
}