package com.easy.cloudConfigClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${easy.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String hello() {
        return this.hello;
    }
}
