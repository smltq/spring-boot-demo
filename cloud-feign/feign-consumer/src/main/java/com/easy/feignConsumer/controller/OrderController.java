package com.easy.feignConsumer.controller;

import com.easy.feignConsumer.service.OrderService;
import com.easy.helloServiceApi.model.Order;
import com.easy.helloServiceApi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/place")
    public Result placeOrder(Order order) throws Exception {

        this.orderService.placeOrder(order);
        return Result.success();
    }
}
