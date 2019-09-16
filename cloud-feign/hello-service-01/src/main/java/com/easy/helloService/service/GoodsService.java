package com.easy.helloService.service;

import com.easy.helloServiceApi.model.Goods;

public interface GoodsService {

    Goods findGoodsById(String goodsId);
}
