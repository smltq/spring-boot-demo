package com.easy.layui.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String city;
    private Number wealth;
    private int sex;
    private boolean lock;
}
