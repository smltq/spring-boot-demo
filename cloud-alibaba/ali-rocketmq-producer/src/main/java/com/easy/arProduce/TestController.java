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
    public String send(String msg) {
        senderService.send(msg);
        return "字符串消息发送成功!";
    }

    @RequestMapping(value = "/sendWithTags", method = RequestMethod.GET)
    public String sendWithTags(String msg) {
        senderService.sendWithTags(msg, "tagStr");
        return "带tag字符串消息发送成功!";
    }

    @RequestMapping(value = "/sendObject", method = RequestMethod.GET)
    public String sendObject(int index) {
        senderService.sendObject(new Foo(index, "foo"), "tagObj");
        return "Object对象消息发送成功!";
    }
}