package com.easy.web.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppProperties {

    @Value("${com.easy.title}")
    private String title;

    @Value("${com.easy.description}")
    private String description;
}
