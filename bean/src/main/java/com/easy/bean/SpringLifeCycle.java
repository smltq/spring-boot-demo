package com.easy.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringLifeCycle {
    public void start() {
        log.info("SpringLifeCycle 开始初始化");
    }

    public void destroy() {
        log.info("SpringLifeCycle 开始销毁");
    }
}