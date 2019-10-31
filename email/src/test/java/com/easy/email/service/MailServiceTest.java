package com.easy.email.service;

import com.easy.email.EmailApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceTest extends EmailApplicationTests {
    @Autowired
    private MailService mailService;

    /**
     * 测试简单邮件
     */
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("smltq@126.com", "这是一封简单邮件", "这是一封普通的SpringBoot测试邮件");
    }
}
