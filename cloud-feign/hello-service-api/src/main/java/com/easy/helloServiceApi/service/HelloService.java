package com.easy.helloServiceApi.service;

import com.easy.helloServiceApi.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface HelloService {

    @GetMapping("hello")
    String hello(@RequestParam("p1") String p1, @RequestParam("p2") String p2);

    @GetMapping("user")
    User user();

    @PostMapping("post")
    String post();
}
