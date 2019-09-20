package com.easy.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class HelloworldApplicationTests {

    @Test
    public void contextLoads() {
    }

//    @Test
//    public void testPwd() {
//        String key = "0123456789654321";
//        AES aes = new AES(Mode.OFB, Padding.NoPadding, key.getBytes(), "9875462154789536".getBytes());
//
//        String content = "中交和美四期！！";
//
//        String encrypt = aes.encryptHex(content);
//        System.out.println(encrypt);
//    }

}
