package com.easy.cloudConfigClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {
    @Value("${easy.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String hello() {
        return hello;
    }
}