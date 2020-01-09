package com.easy.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringLifeCycleProcessor implements BeanPostProcessor {

    /**
     * 预初始化 初始化之前调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("annotationBean".equals(beanName)) {
            log.info("SpringLifeCycleProcessor start beanName={}", beanName);
        }
        return bean;
    }

    /**
     * 后初始化  bean 初始化完成调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("annotationBean".equals(beanName)) {
            log.info("SpringLifeCycleProcessor end beanName={}", beanName);
        }
        return bean;
    }
}