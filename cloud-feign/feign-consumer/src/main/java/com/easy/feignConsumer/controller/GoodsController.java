package com.easy.feignConsumer.controller;

import com.easy.feignConsumer.service.GoodsService;
import com.easy.helloServiceApi.model.Goods;
import com.easy.helloServiceApi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService orderService;

    @RequestMapping("/place")
    public Result placeGoods(Goods goods) {
        Result result = this.orderService.placeGoods(goods);
        return result;
    }
}
