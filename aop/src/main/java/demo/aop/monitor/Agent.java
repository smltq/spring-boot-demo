package demo.aop.monitor;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
//切面类，重复边缘的事情交给中介做
public class Agent {
    @Value("${agent:某某中介}")
    private String agent;

    @Pointcut("execution(* demo.aop.service.Landlord.service())")
    public void IService() {
    }

    @Before("IService()")
    public void before() {
        System.out.println(agent + "带租客看房");
        System.out.println(agent + "谈价格");
    }

    @After("IService()")
    public void after() {
        System.out.println(agent + "交钥匙");
    }

    //  使用 @Around 注解来同时完成前置和后置通知
//    @Around("execution(* demo.aop.service.BigValiant.service())")
//    public void around(ProceedingJoinPoint joinPoint) {
//        System.out.println("带租客看房");
//        System.out.println("谈价格");
//
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//
//        System.out.println("交钥匙");
//    }
}
