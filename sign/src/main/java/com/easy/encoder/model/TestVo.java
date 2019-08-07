package com.easy.encoder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("测试实体")
public class TestVo {
    @ApiModelProperty("编号")
    private int id;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("编码")
    private String code;
}
