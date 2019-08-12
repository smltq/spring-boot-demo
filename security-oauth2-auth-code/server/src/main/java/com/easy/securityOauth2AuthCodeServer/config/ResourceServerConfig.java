package com.easy.securityOauth2AuthCodeServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

public class ResourceServerConfig {

    private static final String RESOURCE_ID = "account";

    @Configuration
    @EnableResourceServer()
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .requestMatchers()
                    // 保险起见，防止被主过滤器链路拦截
                    .antMatchers("/account/**").and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/account/info/**").access("#oauth2.hasScope('get_user_info')")
                    .antMatchers("/account/child/**").access("#oauth2.hasScope('get_childlist')");
        }
    }
}
