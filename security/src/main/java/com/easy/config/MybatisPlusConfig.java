package com.easy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.easy.easy.mapper")
public class MybatisPlusConfig {

}