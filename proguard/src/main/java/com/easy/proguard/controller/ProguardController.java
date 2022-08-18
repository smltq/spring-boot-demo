package com.easy.proguard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProguardController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
