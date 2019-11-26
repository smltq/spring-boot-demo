package com.easy.and.api.service;

import com.easy.and.api.vo.User;

import java.util.Map;

public interface RestService {

    String param(String param);

    String params(int a, String b);

    String headers(String header, String header2, Integer param);

    String pathVariables(String path1, String path2, String param);

    String form(String form);

    User requestBodyMap(Map<String, Object> data, String param);

    Map<String, Object> requestBodyUser(User user);
}
