package com.easy.mybatis.multidatasource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.easy.mybatis.multidatasource.mapper")
public class MybatisPlusConfig {
}