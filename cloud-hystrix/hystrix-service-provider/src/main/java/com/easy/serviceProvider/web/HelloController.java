package com.easy.serviceProvider.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(@RequestParam String p1, @RequestParam String p2) throws Exception {
//        int sleepTime = new Random().nextInt(2000);
//        System.out.println("hello sleep " + sleepTime);
//        Thread.sleep(sleepTime);
        return "hello, " + p1 + ", " + p2;
    }
}