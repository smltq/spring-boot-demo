package com.easy.helloServiceApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;

    private String goodsId;

    private int num;

}
