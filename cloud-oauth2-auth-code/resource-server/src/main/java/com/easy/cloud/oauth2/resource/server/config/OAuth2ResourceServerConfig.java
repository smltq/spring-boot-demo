package com.easy.cloud.oauth2.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 资源服务器配置
 *
 **/
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
  
  @Bean
  public JwtAccessTokenConverter accessTokenConverter(){
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    Resource resource =  new ClassPathResource("public.txt");
    String publicKey;
    try {
      publicKey = inputStream2String(resource.getInputStream());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    converter.setVerifierKey(publicKey);
    converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
    return converter;
  }
  
  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }
  
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    resources.tokenServices(defaultTokenServices);
//    resources.tokenStore(tokenStore());
  }
  
  private String inputStream2String(InputStream is) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(is));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null) {
      builder.append(line);
    }
    return builder.toString();
  }
}
