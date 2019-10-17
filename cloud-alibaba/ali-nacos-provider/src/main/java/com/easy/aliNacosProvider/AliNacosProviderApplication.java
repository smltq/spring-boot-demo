package com.easy.aliNacosProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AliNacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliNacosProviderApplication.class, args);
    }
}
