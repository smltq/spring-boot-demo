package com.easy.mybatis.multidatasource.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 */
@Data
@Accessors(chain = true)
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableField(exist = false)
    private Integer count;
}