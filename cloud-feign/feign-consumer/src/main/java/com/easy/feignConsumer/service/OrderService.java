package com.easy.feignConsumer.service;

import com.easy.helloServiceApi.model.Order;

public interface OrderService {
    void placeOrder(Order order) throws Exception;
}
