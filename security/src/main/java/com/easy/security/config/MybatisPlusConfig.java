package com.easy.security.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.easy.security.mapper")
public class MybatisPlusConfig {

}