package com.easy.cloudConfigClient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTests {

    @Value("${easy.hello}")
    private String hello;

    @Test
    public void contextLoads() {
        System.out.println("************************************************************");
        System.out.println("hello value : " + hello);
        System.out.println("************************************************************");
    }
}
