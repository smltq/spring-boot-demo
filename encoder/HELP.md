# 主流加密算法

## 对称加密

## 非对称加密

## Spring Security 5.x的PasswordEncoderFactories源码解析

PasswordEncoderFactories是Spring Security创建DelegatingPasswordEncoder对象的工厂类。该工厂所创建的DelegatingPasswordEncoder缺省使用bcrypt用于加密，并且能够用于匹配以下几种密码类型 :

- ldap
- MD4
- MD5
- noop (明文密码)
- pbkdf2
- scrypt
- SHA-1
- SHA-256
- sha256