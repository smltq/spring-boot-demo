package com.easy.layui.controller;

import com.easy.layui.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @RequestMapping("/user/getUserList")
    public List<User> getUserList(){
        List<User> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            User vo=new User();
        }
        return list;
    }
}