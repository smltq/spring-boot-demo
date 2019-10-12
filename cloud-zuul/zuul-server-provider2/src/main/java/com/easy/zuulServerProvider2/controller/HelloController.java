package com.easy.zuulServerProvider2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        log.info("request two name is " + name);
        try {
            Thread.sleep(1000000);
        } catch (Exception e) {
            log.error(" hello two error", e);
        }
        return "hello " + name + "，this is two messge";
    }
}