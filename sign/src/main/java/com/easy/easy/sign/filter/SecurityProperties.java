package com.easy.easy.sign.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.security")
@Data
public class SecurityProperties {

    /**
     * 允许忽略签名地址
     */
    List<String> ignoreSignUri;

    /**
     * 签名超时时间(分)
     */
    Integer signTimeout;
}