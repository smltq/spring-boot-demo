# Redis 数据淘汰机制

在 Redis 中，允许用户设置最大使用内存大小 server.maxmemory，在内存限定的情况下是很有用的。譬如，在一台 8G 机子上部署了 4 个 Redis 服务点，每一个服务点分配 1G 的内存大小，减少内存紧张的情况，由此获取更为稳健的服务。Redis 内存数据集大小上升到一定大小的时候，就会施行数据淘汰策略。Redis 提供 6 种数据淘汰策略：


## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
- [本文参考地址](https://zhuanlan.zhihu.com/p/48269420)