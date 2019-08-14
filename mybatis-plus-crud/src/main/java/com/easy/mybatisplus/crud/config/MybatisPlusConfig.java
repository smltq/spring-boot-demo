package com.easy.mybatisplus.crud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.easy.mybatisplus.crud.mapper")
public class MybatisPlusConfig {
}