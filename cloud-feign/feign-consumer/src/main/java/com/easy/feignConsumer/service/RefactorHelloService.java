package com.easy.feignConsumer.service;

import com.easy.helloServiceApi.service.HelloService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hello-service", fallback = HelloServiceFallback.class)
public interface RefactorHelloService extends HelloService {
}
