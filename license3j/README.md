# license3j使用示例

## 主要内容

- 创建密钥对（私钥、公钥）
- 创建许可证
- 签署许可证

## 生成许可证书步骤

### 1.运行工具

    java -jar LicenseTool-3.1.6-SNAPSHOT-jar-with-dependencies.jar

### 2.创建密钥对

    generateKeys algorithm=RSA size=2048 format=BASE64 public=public.key private=private.key

### 3.创建license

    newLicense

### 4.设置属性

    feature name:TYPE=value

- 设置过期时间：feature expiryDate:DATE=2022-12-31 23:59:59
- 设置最大用户数：feature maxUser:INT=10
- 设置mac地址：feature mac:STRING=xxxxxxxxxxx

### 5.生成签名

    sign

### 6.显示公钥

    dumpPublicKey   

### 7.保存license

    saveLicense format=BASE64 license.bin

## 使用帮助

    feature name:TYPE=value                             属性设置
    licenseLoad [format=TEXT*|BINARY|BASE64] fileName   加载license
    saveLicense [format=TEXT*|BINARY|BASE64] fileName   保存license
    loadPrivateKey [format=BINARY*|BASE64] keyFile      加载私钥
    loadPublicKey [format=BINARY*|BASE64] keyFile       加载公钥
    sign [digest=SHA-512]                               生成签名
    generateKeys [algorithm=RSA] [size=2048] [format=BINARY*|BASE64] public=xxx private=xxx     创建密钥对
    verify                                              验证证书
    newLicense                                          创建license
    dumpLicense                                         显示license
    dumpPublicKey                                       显示公钥

    命令缩写
    ll -> licenseload
    lpuk -> loadpublickey
    dl -> dumplicense
    dpk -> dumppublickey
    lprk -> loadprivatekey

## 运行环境

    jdk13+

## 资料

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/license3j/client)
- [license3j](https://github.com/verhas/License3j)
- [license3jrepl](https://github.com/verhas/license3jrepl)