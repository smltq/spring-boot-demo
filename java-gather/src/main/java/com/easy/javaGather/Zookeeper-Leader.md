# Zookeeper之Leader选举过程

Leader在集群中是一个非常重要的角色，负责了整个事务的处理和调度，保证分布式数据一致性的关键所在。既然Leader在ZooKeeper集群中这么重要所以一定要保证集群在任何时候都有且仅有一个Leader存在。

## 概念

### Zookeeper的服务器三种角色：Leader，Follower，Observer。

Leader是Zookeeper 集群工作机制的核心，主要工作：

- a.调度者：集群内部各个服务节点的调度者
- b.事务请求：事务请求的唯一调度和处理者，保证集群事务处理的顺序性

Follower主要职责：

- a.非事务请求：Follower 直接处理非事务请求，对于事务请求，转发给 Leader
- b.Proposal 投票：Leader 上执行事务时，需要 Follower 投票，Leader 才真正执行
- c.Leader 选举投票

Observer主要职责：

- a.非事务请求：Follower 直接处理非事务请求，对于事务请求，转发给 Leader

Observer 跟 Follower的区别：

- a.Follower 参与投票：Leader 选举、Proposal 提议投票（事务执行确认）
- b.Observer 不参与投票：只用于提供非事务请求的处理

### Zookeeper Server的状态

- LOOKING：寻找Leader
- LEADING：Leader状态，对应的节点为Leader。
- FOLLOWING：Follower状态，对应的节点为Follower。
- OBSERVING：Observer状态，对应节点为Observer，该节点不参与Leader选举。

### 其它概念

- ZXID（zookeeper transaction id）：每个改变Zookeeper状态的操作都会形成一个对应的zxid，并记录到transaction log中。 这个值越大，表示更新越新
- myid：服务器SID，一个数字,通过配置文件配置，唯一
- SID：服务器的唯一标识
- 成为Leader的必要条件： Leader要具有最高的zxid；当集群的规模是n时，集群中大多数的机器（至少n/2+1）得到响应并follow选出的Leader。
- 心跳机制：Leader与Follower利用PING来感知对方的是否存活，当Leader无法相应PING时，将重新发起Leader选举。

选举有两种情况，一是服务器启动的投票，二是运行期间的投票。

## 服务器启动时期的Leader选举

### 1.每个服务器发送一个投票(SID,ZXID)

其中sid是自己的myid，初始阶段都将自己投为Leader。

### 2.接收来自其他服务器的投票。

集群的每个服务器收到投票后，首先判断该投票的有效性，如检查是否是本轮投票、是否来自LOOKING状态的服务器。

### 3.处理投票

针对每个投票都按以下规则与自己的投票PK，PK后依据情况是否更新投票，再发送给其他机器。

- a.优先检查ZXID，ZXID较大者优先为Leader
- b.如果ZXID相同，检查SID，SID较大者优先为Leader

### 5.统计投票

每次投票后，服务器统计所有投票，判断是否有过半的机器收到相同的投票，如果某个投票达到一半的要求，则认为该投票提出者可以成为Leader。

### 6.改变服务器状态

一旦确定了Leader，每个服务器都更新自己的状态，Leader变更为Leading，Follower变更为Following
正常情况下一旦选出一个Leader则一直会保持，除非Leader服务器宕掉，则再进行重新选举。

## 服务器运行时期的Leader选举

### 1.变更状态

当Leader宕机后，余下的所非Observer的服务器都会将自己的状态变更为Looking，然后开启新的Leader选举流程。

### 2.每个服务器发出一个投票。

生成(SID,ZXID)信息，注意运行期间的ZXID可能是不同的，但是在投票时都会将自己投为Leader，然后发送给其他的服务器。

### 3.接收来自各个服务器的投票

与启动时过程相同

### 4.处理投票

与启动时过程相同

### 5.统计投票

与启动时过程相同

### 6.改变服务器状态

与启动时过程相同

## 参考资料

[Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
[ZooKeeper 技术内幕：Leader 选举](http://ningg.top/zookeeper-lesson-2-leader-election/)