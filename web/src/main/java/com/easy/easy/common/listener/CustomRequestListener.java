package com.easy.easy.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * 自定义过滤器
 */
@Slf4j
public class CustomRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("监听器：销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        log.info("监听器：初始化");
    }
}
