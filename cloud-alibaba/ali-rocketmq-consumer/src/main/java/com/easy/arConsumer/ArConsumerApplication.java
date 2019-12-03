package com.easy.arConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({MySource.class})
public class ArConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArConsumerApplication.class, args);
    }
}