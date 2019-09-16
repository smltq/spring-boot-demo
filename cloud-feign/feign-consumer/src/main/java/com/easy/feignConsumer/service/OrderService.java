package com.easy.feignConsumer.service;

import com.easy.helloServiceApi.model.Order;
import com.easy.helloServiceApi.vo.Result;

public interface OrderService {
    Result placeOrder(Order order);
}