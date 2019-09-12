package com.easy.helloService.web;

import com.easy.helloServiceApi.dto.User;
import com.easy.helloServiceApi.service.HelloService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController implements HelloService {

    @Override
    public String hello(@RequestParam String p1, @RequestParam String p2) {
        System.out.println("hello service get hello");
        return "hello, " + p1 + ", " + p2;
    }

    @Override
    public User user() {
        System.out.println("hello service get user");
        sleep();
        return new User("Jack", 22);
    }

    @Override
    public String post() {
        System.out.println("hello service post");
        sleep();
        return "post";
    }

    private void sleep() {
        try {
            int sleepTime = new Random().nextInt(2000);
            System.out.println("sleep " + sleepTime);
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
