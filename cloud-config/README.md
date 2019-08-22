# spring-cloud-config 配置中心实现

Spring Cloud Config 用于为分布式系统中的基础设施和微服务应用提供集中化的外部配置支持，分为server端和client端。
server端为分布式配置中心，是一个独立的微服务应用；client端为分布式系统中的基础设置或微服务应用，通过指定配置中心来管理相关的配置。
Spring Cloud Config 构建的配置中心，除了适用于 Spring 构建的应用外，也可以在任何其他语言构建的应用中使用。
Spring Cloud Config 默认采用 Git 存储配置信息，支持对配置信息的版本管理。

## 本示例主要内容

- 配置中心演示client端和server端实现
- 配置文件放在git(因github有时候不太稳定，我放到了国内服务器)
- 版本切换（test、pro、dev）

## Spring Cloud Config 特点

- 提供server端和client端支持(Spring Cloud Config Server和Spring Cloud Config Client);
- 集中式管理分布式环境下的应用配置;
- 基于Spring环境，实现了与Spring应用无缝集成;
- 可用于任何语言开发的程序;
- 默认实现基于Git仓库(也支持SVN)，从而可以进行配置的版本管理，同时也支持配置从本地文件或数据库读取。

## 资料

[官网文档](https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html)