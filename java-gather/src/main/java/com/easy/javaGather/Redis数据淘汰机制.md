# Redis 数据淘汰机制

为了更好的利用内存，使Redis存储的都是缓存的热点数据，Redis设计了相应的内存淘汰机制（也叫做缓存淘汰机制）

通过maxmemory <bytes>配置项来设置允许用户使用的最大内存大小，当内存数据集大小达到一定的大小时，就会根据maxmemory-policy noeviction配置项配置的策略来进行数据淘汰。

## 内存淘汰的过程

- 客户端发起了需要申请更多内存的命令（如set）
- Redis检查内存使用情况，如果已使用的内存大于maxmemory则开始根据用户配置的不同淘汰策略来淘汰内存（key），从而换取一定的内存
- 如果上面都没问题，则这个命令执行成功

## 6 种数据淘汰策略

默认为no-eviction策略

- volatile-lru
> 从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰

- allkeys-lru
> 从数据集（server.db[i].dict）中挑选最近最少使用的数据淘汰

- volatile-ttl
> 从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰

- volatile-random
> 从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰

- allkeys-random
> 从数据集（server.db[i].dict）中任意选择数据淘汰

- no-enviction
> 禁止驱逐数据，永远不过期，仅对写操作返回一个错误，默认为该项

Redis 确定驱逐某个键值对后，会删除这个数据，并将这个数据变更消息发布到本地（AOF 持久化）和从机（主从连接）

### LRU 数据淘汰机制

实际上Redis实现的LRU并不是可靠的LRU，也就是名义上我们使用LRU算法淘汰键，但是实际上被淘汰的键并不一定是真正的最久没用的，这里涉及到一个权衡的问题，如果需要在全部键空间内搜索最优解，则必然会增加系统的开销，Redis是单线程的，也就是同一个实例在每一个时刻只能服务于一个客户端，所以耗时的操作一定要谨慎。为了在一定成本内实现相对的LRU，早期的Redis版本是基于采样的LRU，也就是放弃全部键空间内搜索解改为采样空间搜索最优解。自从Redis3.0版本之后，Redis作者对于基于采样的LRU进行了一些优化，目的是在一定的成本内让结果更靠近真实的LRU。

### TTL 数据淘汰机制

Redis 数据集数据结构中保存了键值对过期时间的表，即 redisDb.expires，在使用 SET 命令的时候，就有一个键值对超时时间的选项。
从过期时间 redisDB.expires 表中随机挑选几个键值对，取出其中 ttl 最大的键值对淘汰。同样TTL淘汰策略并不是所有过期时间的表中最快过期的键值对，而只是随机挑选的几个键值对。

### 随机淘汰

在随机淘汰的场景下获取待删除的键值对，随机找hash桶再次hash指定位置的dictEntry即可

## 总结

Redis中的淘汰机制（LRU和TTL）都是非精确算法实现的，主要从性能和可靠性上做平衡，所以并不是完全可靠，在了解Redis淘汰策略之后还应在平时多主动设置或更新key的expire时间，主动删除没有价值的数据，提升Redis整体性能和空间

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
- [本文参考地址](https://zhuanlan.zhihu.com/p/48269420)