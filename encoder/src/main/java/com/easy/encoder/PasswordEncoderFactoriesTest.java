package com.easy.encoder;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderFactoriesTest {
    public static void main(String[] args) {
        // 返回的encoder实现类实际上是 DelegatingPasswordEncoder , 它其实是一个 PasswordEncoder 代理,
        // 代理了其他一组 PasswordEncoder
        // DelegatingPasswordEncoder 用于密码匹配的密码密文必须符合格式 : "{encoderId}xxxxxxx",
        // 它所代理的某个 PasswordEncoder 所能接收的密码密文应该是上面例子密码中的 "xxxxxxx" 部分
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        {
            // 测试1:缺省用于加密的是 BCryptPasswordEncoder
            System.out.println("测试1:缺省用于加密的是 BCryptPasswordEncoder");
            final String passwordPlainText = "passw0rdIsTiger";
            final String passwordCypher = encoder.encode(passwordPlainText);
            System.out.printf("密码明文是 : %s\n", passwordPlainText);
            System.out.printf("密码密文是 : %s\n", passwordCypher);


            final String expectedPrefix = "{bcrypt}";
            System.out.printf("密码密文前缀是 %s : %s\n", expectedPrefix, passwordCypher.startsWith(expectedPrefix));
            final boolean match = encoder.matches(passwordPlainText, passwordCypher);
            System.out.printf("密码密文和密码明文匹配 : %s\n", match);
        }

        {
            // 测试2:工厂产生的 PasswordEncoder 会根据密码密文encoderId前缀对应的PasswordEncoder进行密码匹配
            System.out.println("测试2:工厂产生的 PasswordEncoder 会根据密码密文encoderId前缀对应的PasswordEncoder进行密码匹配");
            final String passwordPlainText = "password";
            String cypher1 = "{noop}password";
            String cypher2 = "{pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc";
            String cypher3 = "{scrypt}$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc=";
            String cypher4 = "{sha256}97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0";

            System.out.printf("密码明文 : %s\n", passwordPlainText);
            System.out.printf("密码密文1 : %s\n", cypher1);
            System.out.printf("密码密文2 : %s\n", cypher2);
            System.out.printf("密码密文3 : %s\n", cypher3);
            System.out.printf("密码密文4 : %s\n", cypher4);

            System.out.printf("密码密文1和密码明文匹配 : %s\n", encoder.matches(passwordPlainText, cypher1));
            System.out.printf("密码密文2和密码明文匹配 : %s\n", encoder.matches(passwordPlainText, cypher2));
            System.out.printf("密码密文3和密码明文匹配 : %s\n", encoder.matches(passwordPlainText, cypher3));
            System.out.printf("密码密文4和密码明文匹配 : %s\n", encoder.matches(passwordPlainText, cypher4));
        }
    }
}
