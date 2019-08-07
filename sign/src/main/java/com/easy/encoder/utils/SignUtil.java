package com.easy.encoder.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.SortedMap;

/**
 * 签名工具类
 */
@Slf4j
public class SignUtil {

    /**
     * 验证签名
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(SortedMap<String, String> params, String sign, Long timestamp) {
        String paramsJsonStr = "Timestamp" + timestamp + JSONObject.toJSONString(params);
        return verifySign(paramsJsonStr, sign);
    }

    /**
     * 验证签名
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(String params, String sign) {
        log.info("Header Sign : {}", sign);
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("Param : {}", params);
        String paramsSign = getParamsSign(params);
        log.info("Param Sign : {}", paramsSign);
        return sign.equals(paramsSign);
    }

    /**
     * @return 得到签名
     */
    public static String getParamsSign(String params) {
        return DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
    }
}
