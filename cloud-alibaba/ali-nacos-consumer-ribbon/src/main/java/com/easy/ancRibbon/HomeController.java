package com.easy.ancRibbon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/", produces = "application/json")
    public String home() {
        log.info("-----------------consumer调用开始-----------------");
        String param = "云天";
        log.info("消费者传递参数：" + param);
        String result = restTemplate.getForObject("http://ali-nacos-provider/hello/" + param, String.class);
        log.info("收到提供者响应：" + result);
        return "ribbon消费者，" + result;
    }
}
