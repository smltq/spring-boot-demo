package com.easy.securityOauth2Credentials;

import cn.hutool.core.util.StrUtil;
import com.easy.securityOauth2Credentials.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class TestUserService {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testPassword() {
        //用户权限列表
        Collection<? extends GrantedAuthority> authorities = userService.queryUserAuthorities(2);
        log.info("用户权限列表==>{}", StrUtil.toString(authorities));
    }
}
