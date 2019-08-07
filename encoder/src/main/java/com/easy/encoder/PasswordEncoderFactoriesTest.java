package com.easy.encoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class PasswordEncoderFactoriesTest {
    public static void main(String[] args) {
        String password = "123456";
        PasswordEncoder defaultEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        log.info("PasswordEncoderFactories默认加密算法是bcrypt");
        String encodeStr1 = defaultEncoder.encode(password);
        String encodeStr2 = defaultEncoder.encode(password);
        log.info("明文==>{},密文==>{},是否匹配==>{}", password, encodeStr1, defaultEncoder.matches(password, encodeStr1));
        log.info("明文==>{},密文==>{},是否匹配==>{}", password, encodeStr2, defaultEncoder.matches(password, encodeStr2));

        // 测试2:工厂产生的 PasswordEncoder 会根据密码密文encoderId前缀对应的PasswordEncoder进行密码匹配
        System.out.println("测试2:工厂产生的 PasswordEncoder 会根据密码密文encoderId前缀对应的PasswordEncoder进行密码匹配");
        final String passwordPlainText = "password";
        String cypher1 = "{noop}password";
        String cypher2 = "{pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc";
        String cypher4 = "{sha256}97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0";

        log.info("密码密文1和密码明文匹配 :{}", defaultEncoder.matches(passwordPlainText, cypher1));
        log.info("密码密文2和密码明文匹配 :{}", defaultEncoder.matches(passwordPlainText, cypher2));
        log.info("密码密文4和密码明文匹配 :{}", defaultEncoder.matches(passwordPlainText, cypher4));
    }
}