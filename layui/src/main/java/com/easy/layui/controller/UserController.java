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
    public List<User> getUserList() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User vo = new User();
            vo.setId(i);
            vo.setUsername("测试用户" + i);
            vo.setCity("城市" + i);
            vo.setWealth(i * 10);
            vo.setSex(i % 2);
            vo.setLock(false);
            list.add(vo);
        }
        return list;
    }
}