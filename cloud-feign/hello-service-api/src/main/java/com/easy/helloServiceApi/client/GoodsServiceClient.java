package com.easy.helloServiceApi.client;

import com.easy.helloServiceApi.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "GOODS")
public interface GoodsServiceClient {

    @RequestMapping("/goods/goodsInfo/{goodsId}")
    Result goodsInfo(@PathVariable("goodsId") String goodsId);
}