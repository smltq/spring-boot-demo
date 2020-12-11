package com.easy.helloworld;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HelloWorldApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPwd() {
        String key = "0123456789654321";
        AES aes = new AES(Mode.OFB, Padding.NoPadding, key.getBytes(), "9875462154789536".getBytes());

        String content = "中交和美四期！！";

        String encrypt = aes.encryptHex(content);
        System.out.println(encrypt);
    }

}
