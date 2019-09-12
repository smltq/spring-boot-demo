package com.easy.feignConsumer.web;

import com.easy.feignConsumer.service.RefactorHelloService;
import com.easy.helloServiceApi.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignConsumerController {

    @Autowired
    RefactorHelloService refactorHelloService;

    @GetMapping("hello")
    public String hello(@RequestParam String p1, @RequestParam String p2) {
        System.out.println("feign consumer get hello");
        return refactorHelloService.hello(p1, p2);
    }

    @GetMapping("user")
    public User user() {
        System.out.println("feign consumer get user");
        return refactorHelloService.user();
    }

    @PostMapping("post")
    public String post() {
        System.out.println("feign consumer post");
        return refactorHelloService.post();
    }

}
