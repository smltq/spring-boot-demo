package com.easy.sign.constant;

/**
 * 网络请求常量
 **/
public class RequestConstants {

    public static final String SUCCESS = "200";

    public static final String FAIL = "500";

    public static final String BAD_REQUEST = "400";

    /**
     * 未授权
     */
    public static final String UNAUTHORIZED = "401";

    /**
     * 未登录
     */
    public static final String NOTLOGIN = "403";

    public static final String CREATED = "200";

    public static final String UPDATED = "200";

    public static final String GONE = "410";

    public static final String SUCCESS_MSG = "操作成功";

    public static final String FAIL_MSG = "操作失败";

    public static final String ILLEGAL_PARAMS = "参数有误";

    public static final String UNAUTHORIZED_MSG = "未授权";

    public static final String NOT_LOGIN_MSG = "未登录";

    /**
     * 第三方接口成功响应词
     */
    public static final String RES_SUCCESS_WORD = "success";

    /**
     * 第三方接口响应字段
     */
    public static final String RES_BODY_DATA = "data";

    /**
     * 第三方接口响应字段
     */
    public static final String RES_BODY_FOOTER = "footer";

    /**
     * 是否超过5000条标识
     */
    public static final String RES_BODY_GREATER5K = "greater5K";
}
