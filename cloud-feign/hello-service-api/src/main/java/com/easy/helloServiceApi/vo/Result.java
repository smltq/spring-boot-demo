package com.easy.helloServiceApi.vo;


import lombok.Getter;

import java.io.Serializable;

@Getter
public class Result implements Serializable {

    private static final long serialVersionUID = -8143412915723961070L;

    private int code;

    private String msg;

    private Object data;

    private Result() {
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result fail() {
        return fail(500, "fail");
    }

    public static Result fail(int code, String message) {
        return new Result(code, message);
    }
}
