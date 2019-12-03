package com.easy.arProduce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({MySource.class})
public class ArProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArProduceApplication.class, args);
    }
}
