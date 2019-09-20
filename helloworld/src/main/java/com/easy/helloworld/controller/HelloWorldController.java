package com.easy.helloworld.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index(String p1, String p2) {

        AES aes = new AES(Mode.OFB, Padding.NoPadding, p1.getBytes(), "9875462154789536".getBytes());

        String decryptStr = aes.decryptStr(p2, CharsetUtil.CHARSET_UTF_8);
        return decryptStr;
    }
}