package com.easy.feignConsumer.service.impl;

import com.easy.feignConsumer.service.OrderService;
import com.easy.helloServiceApi.client.GoodsServiceClient;
import com.easy.helloServiceApi.model.Order;
import com.easy.helloServiceApi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodsServiceClient goodsServiceClient;

    @Override
    public Result placeOrder(Order order) {

        Result result = this.goodsServiceClient.goodsInfo(order.getGoodsId());

        if (result != null && result.getCode() == 200) {
            System.out.println("=====下订单====");
            System.out.println(result.getData());
        }
        return result;
    }
}
