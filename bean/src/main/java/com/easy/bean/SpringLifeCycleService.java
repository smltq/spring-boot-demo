package com.easy.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpringLifeCycleService implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("SpringLifeCycleService 开始");
    }

    @Override
    public void destroy() throws Exception {
        log.info("SpringLifeCycleService 销毁");
    }
}