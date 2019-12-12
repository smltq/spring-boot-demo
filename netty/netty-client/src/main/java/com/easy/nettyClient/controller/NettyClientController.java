package com.easy.nettyClient.controller;

import com.easy.nettyClient.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NettyClientController {

    @Autowired
    private NettyClient nettyClient;

    @GetMapping("/send")
    public String send(String msg) {
        nettyClient.sendMsg(msg);
        return "发送成功！";
    }
}
