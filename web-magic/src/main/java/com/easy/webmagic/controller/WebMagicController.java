package com.easy.webmagic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebMagicController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}