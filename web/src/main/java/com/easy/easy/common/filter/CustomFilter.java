package com.easy.easy.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义过滤器
 */
@Slf4j
public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("doFilter 请求处理");
        //对request、response进行一些预处理
        // 比如设置请求编码
        request.setCharacterEncoding("UTF-8");

        //TODO 进行业务逻辑

        //链路 直接传给下一个过滤器
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("filter 销毁");
    }
}