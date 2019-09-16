package com.easy.feignConsumer.service.impl;

import com.easy.feignConsumer.service.OrderService;
import com.easy.helloServiceApi.client.GoodsServiceClient;
import com.easy.helloServiceApi.model.Order;
import com.easy.helloServiceApi.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodsServiceClient goodsServiceClient;

    @Override
    public Result placeOrder(Order order) {

        Result result = this.goodsServiceClient.goodsInfo(order.getGoodsId());

        if (result != null && result.getCode() == 200) {
            log.info("=====下订单====");
            log.info("接口返回数据为==>{}", ToStringBuilder.reflectionToString(result.getData()));
        }
        return result;
    }
}
