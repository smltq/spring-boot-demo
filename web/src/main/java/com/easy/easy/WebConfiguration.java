package com.easy.easy;

import com.easy.easy.common.filter.CustomFilter;
import com.easy.easy.common.interceptor.CustomHandlerInterceptor;
import com.easy.easy.common.listener.CustomRequestListener;
import com.easy.easy.common.listener.CustomSessionListener;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomHandlerInterceptor())
                .addPathPatterns("/*");
    }

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean customFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new CustomFilter());
        //过滤器名称
        registration.setName("CustomFilter");
        //拦截规则
        registration.addUrlPatterns("/*");
        //过滤器顺序
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean customSessionListener() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new CustomSessionListener());
        return srb;
    }

    @Bean
    public ServletListenerRegistrationBean customRequestListener() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new CustomRequestListener());
        return srb;
    }
}



