package com.easy.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class AnnotationBean {
    @PostConstruct
    public void start() {
        log.info("AnnotationBean 开始初始化");
    }

    @PreDestroy
    public void destroy() {
        log.info("AnnotationBean 开始销毁");
    }
}