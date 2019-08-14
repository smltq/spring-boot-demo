package com.easy.securityOauth2Credentials.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.easy.securityOauth2Credentials.mapper")
public class MybatisPlusConfig {
}