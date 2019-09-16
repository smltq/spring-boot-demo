package com.easy.helloServiceApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Goods {

    private String goodsId;

    private String name;

    private String descr;

    // 测试端口
    private int port;
}
