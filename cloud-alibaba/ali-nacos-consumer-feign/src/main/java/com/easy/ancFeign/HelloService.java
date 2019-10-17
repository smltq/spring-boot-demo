package com.easy.ancFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ali-nacos-provider")
public interface HelloService {

    @RequestMapping(path = "hello/{str}")
    String hello(@RequestParam("str") String param);

}
