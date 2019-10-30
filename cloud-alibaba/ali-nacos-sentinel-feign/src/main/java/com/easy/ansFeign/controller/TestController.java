package com.easy.ansFeign.controller;

import com.easy.ansFeign.service.HelloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello-feign/{str}")
    public String feign(@PathVariable String str) {
        return helloService.hello(str);
    }
}
