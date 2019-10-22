package com.easy.anConfigClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @Value("${easy.hello}")
    private String hello;

    @Value("${easy.myconfig}")
    private String myconfig;

    @RequestMapping("/hello")
    public Map hello() {
        Map map = new HashMap<>();
        map.put("hello", hello);
        map.put("myconfig", myconfig);
        return map;
    }
}