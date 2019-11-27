package com.easy.andConsumer;

import com.easy.and.api.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Reference
    HelloService helloService;

    @GetMapping("/hello")
    public String hello(String name) {
        return helloService.hello("云天");
    }
}