package com.easy.securityOauth2Credentials.web;

import com.easy.web.common.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ProPropertiesTest {

    @Autowired
    private AppProperties appProperties;

    @Test
    public void getProperties() {
        log.info(appProperties.getTitle());
        log.info(appProperties.getDescription());
    }
}