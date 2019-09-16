package com.easy.helloService.service.impl;

import com.easy.helloService.service.GoodsService;
import com.easy.helloServiceApi.model.Goods;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    // 模拟数据库
    private static Map<String, Goods> data;

    static {
        data = new HashMap<>();
        data.put("1", new Goods("1", "华为", "华为手机", 8081));
        data.put("2", new Goods("2", "苹果", "苹果", 8081));
    }

    @Override
    public Goods findGoodsById(String goodsId) {
        return data.get(goodsId);
    }
}
