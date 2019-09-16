package com.easy.feignConsumer.service;

import com.easy.helloServiceApi.model.Goods;
import com.easy.helloServiceApi.vo.Result;

public interface GoodsService {
    Result placeGoods(Goods goods);
}