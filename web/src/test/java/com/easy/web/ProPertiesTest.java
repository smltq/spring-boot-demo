package com.easy.web;

import com.easy.common.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProPertiesTest {

    @Autowired
    private AppProperties appProperties;

    @Test
    public void getProperties() {
        log.info(appProperties.getTitle());
        log.info(appProperties.getDescription());
    }
}