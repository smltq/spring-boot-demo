package com.easy.cloud.oauth2.resource.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ResourceServerApplication {
  
  private static final Logger log = LoggerFactory.getLogger(ResourceServerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ResourceServerApplication.class, args);
  }
  
  @GetMapping("/user")
  public Authentication getUser(Authentication authentication) {
    log.info("resource: user {}", authentication);
    return authentication;
  }
}
