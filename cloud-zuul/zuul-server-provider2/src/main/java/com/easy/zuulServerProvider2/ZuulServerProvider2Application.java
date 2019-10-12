package com.easy.zuulServerProvider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZuulServerProvider2Application {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerProvider2Application.class, args);
    }
}
