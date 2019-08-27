package com.easy.mybatis.multidatasource.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(com.easy.mybatis.multidatasource.annotation.Slave) " +
            "|| (execution(* com.easy.mybatis.multidatasource.service..*.select*(..)) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.easy.mybatis.multidatasource.annotation.Master) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.insert*(..)) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.add*(..)) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.update*(..)) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.edit*(..)) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.delete*(..)) " +
            "|| execution(* com.easy.mybatis.multidatasource.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContext.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContext.master();
    }
}