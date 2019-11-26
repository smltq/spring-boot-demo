
package com.easy.andProviderWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAutoConfiguration
public class AndProviderWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AndProviderWebApplication.class);
    }
}
