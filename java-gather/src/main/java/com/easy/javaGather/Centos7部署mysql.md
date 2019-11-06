# Centos7部署mysql

## 安装mysql

> yum install mysql mysql-server

会出现以下错误：
```cfml
[root@yl-web yl]# yum install mysql-server
Loaded plugins: fastestmirror
Loading mirror speeds from cached hostfile
 * base: mirrors.sina.cn
 * extras: mirrors.sina.cn
 * updates: mirrors.sina.cn
No package mysql-server available.
Error: Nothing to do
```
出现这个问题的原因是，CentOS 7 版本将MySQL数据库软件从默认的程序列表中移除，用mariadb代替。

### 解决办法1：安装mariadb

MariaDB数据库管理系统是MySQL的一个分支，主要由开源社区在维护，采用GPL授权许可。开发这个分支的原因之一是：甲骨文公司收购了MySQL后，有将MySQL闭源的潜在风险，因此社区采用分支的方式来避开这个风险。MariaDB的目的是完全兼容MySQL，包括API和命令行，使之能轻松成为MySQL的代替品。

安装mariadb，文件几十M左右大小

> yum install mariadb-server mariadb 

mariadb数据库的相关命令是：

- systemctl start mariadb  #启动MariaDB
- systemctl stop mariadb  #停止MariaDB
- systemctl restart mariadb  #重启MariaDB
- systemctl enable mariadb  #设置开机启动

### 解决办法2：官网下载安装mysql-server

安装前，我们可以检测系统是否自带安装 MySQL:

> rpm -qa | grep mysql

如果系统有安装，那可以选择进行卸载:

```cfml
rpm -e mysql  // 普通删除模式
rpm -e --nodeps mysql // 强力删除模式，如果使用上面命令删除时，提示有依赖的其它文件，则用该命令可以对其进行强力删除
```

1.安装 MySQL

接下来我们在 Centos7 系统下使用 yum 命令安装 MySQL，需要注意的是 CentOS 7 版本中 MySQL数据库已从默认的程序列表中移除，所以在安装前我们需要先去官网下载 Yum 资源包，下载地址为：https://dev.mysql.com/downloads/repo/yum/

```cfml
wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm
rpm -ivh mysql-community-release-el7-5.noarch.rpm
yum update
yum install mysql-server
```

2.初始化 MySQL

```cfml
mysqld --initialize
```

3.启动 MySQL

```cfml
systemctl start mysqld
```

4.查看 MySQL 运行状态

```cfml
systemctl status mysqld
```

5.验证 MySQL 安装

```cfml
mysqladmin --version
```

输出类似以下信息，表示安装成功了

> mysqladmin  Ver 8.42 Distrib 5.6.46, for Linux on x86_64

如果以上命令执行后未输出任何信息，说明你的Mysql未安装成功。

6.密码设置

```cfml
mysqladmin -u root password "new_password";
```

## navicat 远程访问mysql失败，修改配置如下

1.首先配置允许访问的用户，采用授权的方式给用户权限

```cfml
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'IDENTIFIED BY '123456' WITH GRANT OPTION;
```

root是登陆数据库的用户，123456是登陆数据库的密码

2.配置好权限之后，刷新生效

flush privileges;

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)