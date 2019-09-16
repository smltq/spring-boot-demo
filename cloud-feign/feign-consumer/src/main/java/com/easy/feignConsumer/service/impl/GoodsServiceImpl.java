package com.easy.feignConsumer.service.impl;

import com.easy.feignConsumer.service.GoodsService;
import com.easy.helloServiceApi.client.GoodsServiceClient;
import com.easy.helloServiceApi.model.Goods;
import com.easy.helloServiceApi.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsServiceClient goodsServiceClient;

    @Override
    public Result placeGoods(Goods order) {

        Result result = this.goodsServiceClient.goodsInfo(order.getGoodsId());

        if (result != null && result.getCode() == 200) {
            log.info("=====获取本地商品====");
            log.info("接口返回数据为==>{}", ToStringBuilder.reflectionToString(result.getData()));
        }
        return result;
    }
}
