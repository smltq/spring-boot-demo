# Windows上部署MySql

## 下载安装包

最新版本可以在 [MySQL 下载](https://dev.mysql.com/downloads/mysql/)中下载。

下载完后，我们将 zip 包解压到相应的目录，这里我将解压后的文件夹放在 D:\Program Files\Java\mysql-8.0.16-winx64下

## 配置 MySQL 的配置文件
   
打开刚刚解压的文件夹 D:\Program Files\Java\mysql-8.0.16-winx64 ，在该文件夹下创建 my.ini 配置文件，编辑 my.ini 配置以下基本信息：

```cfml
[mysql] 
default-character-set=utf8 
[mysqld] 
basedir=D:\Program Files\Java\mysql-8.0.16-winx64
datadir=D:\Program Files\Java\mysql-8.0.16-winx64\data 
port=3306 
max_connections=200 
character-set-server=utf8 
default-storage-engine=INNODB
```

这里我们把data一起放到mysql-8.0.16-winx64目录，所以在该目录下需要创建data文件

## 初始化并启动 MySQL 数据库
   
### 1.以管理员身份打开 cmd 命令行工具，切换目录

    cd D:\Program Files\Java\mysql-8.0.16-winx64\bin
    
### 2.初始化数据库
    
    mysqld --initialize --console
    
执行完成后，会输出 root 用户的初始默认密码，如：
    
    ...
    2019-09-28T02:35:05.464644Z 5 [Note] [MY-010454] [Server] A temporary password is generated for root@localhost: J0kqlpkJ,CKz
    ...

J0kqlpkJ,CKz 就是初始密码，后续登录需要用到，你也可以在登陆后修改密码。

### 3.输入以下安装命令

    mysqld install

### 4.启动输入以下命令即可

    net start mysql
    
注意: 在 5.7 需要初始化 data 目录：

    cd D:\Program Files\Java\mysql-8.0.16-winx64\bin
    mysqld --initialize-insecure 
    
初始化后再运行 net start mysql 即可启动 mysq

## 登录 MySQL

当 MySQL 服务已经运行时, 我们可以通过 MySQL 自带的客户端工具登录到 MySQL 数据库中, 首先打开命令提示符, 输入以下格式的命名:

    mysql -h 主机名 -u 用户名 -p

参数说明：

- -h : 指定客户端所要登录的 MySQL 主机名, 登录本机(localhost 或 127.0.0.1)该参数可以省略;
- -u : 登录的用户名;
- -p : 告诉服务器将会使用一个密码来登录, 如果所要登录的用户名密码为空, 可以忽略此选项。

如果我们要登录本机的 MySQL 数据库，只需要输入以下命令即可：

    mysql -u root -p
    
按回车确认, 如果安装正确且 MySQL 正在运行, 会得到以下响应:

Enter password:
若密码存在, 输入密码登录, 不存在则直接按回车登录。登录成功后你将会看到 Welcome to the MySQL monitor... 的提示语。

然后命令提示符会一直以 mysq> 加一个闪烁的光标等待命令的输入, 输入 exit 或 quit 退出登录。

## 使用Navicat for MySQL连接mysql

### 连接报错

Navicat连接MySQL Server8.0版本时出现Client does not support authentication protocol requested by server; consider upgrading MySQL client

### 解决方案

出现连接失败的原因：mysql8 之前的版本中加密规则是mysql_native_password,而在mysql8之后,加密规则是caching_sha2_password。两种解决方案

1.升级navicat，由于navicat是收费的，个人感觉升级会比较麻烦点。

2.把用户密码登录的加密规则还原成mysql_native_password这种加密方式，本人选择第二种解决方案

### 具体步骤

#### 1.命令行登录mysql

    mysql -u root -p

#### 2.输入命令修改密码方法

    ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';
 
#### 3.刷新权限，使自己的修改生效

    FLUSH PRIVILEGES;

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
- [原文地址](https://github.com/smltq/spring-boot-demo/blob/master/java-gather/src/main/java/com/easy/javaGather/Windows%E4%B8%8A%E9%83%A8%E7%BD%B2MySql.md)
