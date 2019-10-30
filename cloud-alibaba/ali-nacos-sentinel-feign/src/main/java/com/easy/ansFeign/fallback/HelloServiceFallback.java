package com.easy.ansFeign.fallback;

import com.easy.ansFeign.service.HelloService;

public class HelloServiceFallback implements HelloService {

    private Throwable throwable;

    HelloServiceFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * 调用服务提供方的输出接口.
     *
     * @param str 用户输入
     * @return
     */
    @Override
    public String hello(String str) {
        return "服务调用失败，降级处理。异常信息：" + throwable.getMessage();
    }
}
