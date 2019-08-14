package com.easy.credentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CredentialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CredentialsApplication.class, args);
    }

}