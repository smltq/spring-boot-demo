package com.easy.arProduce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    SenderService senderService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(String msg) throws Exception {
        senderService.send(msg);
        return "消息发送成功!";
    }
}
