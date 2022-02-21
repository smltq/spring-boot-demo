package com.easy.sign.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONTokener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * http 工具类 获取请求中的参数
 */
@Slf4j
public class HttpUtil {
    /**
     * 获取 Body 参数
     *
     * @param request
     */
    public static SortedMap<String, String> getBodyParams(final HttpServletRequest request) throws IOException {
        SortedMap<String, String> result = new TreeMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str;
        StringBuilder wholeStr = new StringBuilder();
        //一行一行的读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }

        if (StrUtil.isEmpty(wholeStr)) {
            wholeStr.append("{}");
        }

        //转化成json对象
        Object json = new JSONTokener(wholeStr.toString()).nextValue();
        if (json instanceof JSONObject) {
            result = JSONObject.parseObject(wholeStr.toString(), SortedMap.class);
        } else if (json instanceof JSONArray) {
            result.put("array", wholeStr.toString());
        }
        return result;
    }

    /**
     * 将URL请求参数转换成Map
     *
     * @param request
     */
    public static SortedMap<String, String> getUrlParams(HttpServletRequest request) {
        String param = "";
        SortedMap<String, String> result = new TreeMap<>();
        if (StrUtil.isEmpty(request.getQueryString())) {
            return result;
        }

        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] params = param.split("&");
        for (String s : params) {
            int index = s.indexOf("=");
            result.put(s.substring(0, index), s.substring(index + 1));
        }
        return result;
    }
}
