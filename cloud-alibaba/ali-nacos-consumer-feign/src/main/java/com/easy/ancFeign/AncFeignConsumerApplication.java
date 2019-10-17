package com.easy.ancFeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.easy.ancFeign","com.easy"})
public class AncFeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AncFeignConsumerApplication.class, args);
    }

}
