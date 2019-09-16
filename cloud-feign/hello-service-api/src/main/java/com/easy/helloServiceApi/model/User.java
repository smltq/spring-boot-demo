package com.easy.helloServiceApi.model;

import lombok.Data;

/**
 * 用户类
 */
@Data
public class User {

    private int id;

    private String username;

    private String password;

    private int port;

    public User() {

    }

    public User(int id, String username, String password, int port) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.port = port;
    }
}
