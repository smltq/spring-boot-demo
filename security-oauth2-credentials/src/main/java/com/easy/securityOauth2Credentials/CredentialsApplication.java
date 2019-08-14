package com.easy.securityOauth2Credentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//可以使用：basePackageClasses={},basePackages={}
@ComponentScan("com.easy.securityOauth2Credentials.mapper")
@SpringBootApplication
public class CredentialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CredentialsApplication.class, args);
    }

}