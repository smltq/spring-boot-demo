package com.easy.web.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
public class CustomSessionListener implements HttpSessionListener {

    public static int online = 0;

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        System.out.println("创建session");
        online++;
        log.info("当前在线人数：" + online);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        System.out.println("销毁session");
    }
}