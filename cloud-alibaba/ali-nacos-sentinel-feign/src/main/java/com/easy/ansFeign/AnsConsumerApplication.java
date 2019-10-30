package com.easy.ansFeign;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
public class AnsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnsConsumerApplication.class, args);
    }

}
