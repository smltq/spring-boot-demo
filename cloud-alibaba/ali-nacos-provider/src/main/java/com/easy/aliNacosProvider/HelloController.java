package com.easy.aliNacosProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
    @GetMapping(value = "/hello/{str}", produces = "application/json")
    public String hello(@PathVariable String str) {
        log.info("-----------收到消费者请求-----------");
        log.info("收到消费者传递的参数：" + str);
        String result = "我是服务提供者，见到你很高兴==>" + str;
        log.info("提供者返回结果：" + result);
        return result;
    }
}
