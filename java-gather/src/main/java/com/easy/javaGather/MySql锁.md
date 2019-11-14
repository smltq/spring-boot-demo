# MySql锁

MySQL InnoDB支持三种行锁定

- 行锁（Record Lock）:锁直接加在索引记录上面，锁住的是key。

- 间隙锁（Gap Lock）:锁定索引记录间隙，确保索引记录的间隙不变。间隙锁是针对事务隔离级别为可重复读或以上级别而设计的。

- 后码锁（Next-Key Lock）：行锁和间隙锁组合起来就叫Next-Key Lock。

默认情况下，InnoDB工作在可重复读隔离级别下，并且会以Next-Key Lock的方式对数据行进行加锁，这样可以有效防止幻读的发生。Next-Key Lock是行锁和间隙锁的组合，当InnoDB扫描索引记录的时候，会首先对索引记录加上行锁（Record Lock），再对索引记录两边的间隙加上间隙锁（Gap Lock）。加上间隙锁之后，其他事务就不能在这个间隙修改或者插入记录。

## 行锁(Record Lock)

- 当需要对表中的某条数据进行写操作（insert、update、delete、select for update）时，需要先获取记录的排他锁（X锁），这个就称为行锁。

```sql
create table x(`id` int, `num` int, index `idx_id` (`id`));
insert into x values(1, 1), (2, 2);

-- 事务A
START TRANSACTION;
update x set id = 1 where id = 1;

-- 事务B
-- 如果事务A没有commit，id=1的记录拿不到X锁，将出现等待
START TRANSACTION;
update x set id = 1 where id = 1;

-- 事务C
-- id=2的记录可以拿到X锁，不会出现等待
START TRANSACTION;
update x set id = 2 where id = 2;
```

- 针对InnoDB RR隔离级别，上述SQL示例展示了行锁的特点：“锁定特定行不允许进行修改”，但行锁是基于表索引的，如果where条件中用的是num字段（非索引列）将产生不一样的现象：

```sql
-- 事务A
START TRANSACTION;
update x set num = 1 where num = 1;

-- 事务B
-- 由于事务A中num字段上没有索引将产生表锁，导致整张表的写操作都会出现等待
START TRANSACTION;
update x set num = 1 where num = 1;

-- 事务C
-- 同理，会出现等待
START TRANSACTION;
update x set num = 2 where num = 2;

-- 事务D
-- 等待
START TRANSACTION;
insert into x values(3, 3);
```

## Gap锁(Gap Lock)

在MySQL中select称为快照读，不需要锁，而insert、update、delete、select for update则称为当前读，需要给数据加锁，幻读中的“读”即是针对当前读。

RR事务隔离级别允许存在幻读，但InnoDB RR级别却通过Gap锁避免了幻读

### 产生间隙锁的条件（RR事务隔离级别下）

- 使用普通索引锁定
- 使用多列唯一索引
- 使用唯一索引锁定多行记录

### 唯一索引的间隙锁

测试环境

    MySQL，InnoDB，默认的隔离级别（RR）

数据表

```sql
CREATE TABLE `test` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `name` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

数据

```sql
INSERT INTO `test` VALUES ('1', '小罗');
INSERT INTO `test` VALUES ('5', '小黄');
INSERT INTO `test` VALUES ('7', '小明');
INSERT INTO `test` VALUES ('11', '小红');
```

以上数据，会生成隐藏间隙

(-infinity, 1]
(1, 5]
(5, 7]
(7, 11]
(11, +infinity]

#### 只使用记录锁，不会产生间隙锁

```sql
/* 开启事务1 */
BEGIN;
/* 查询 id = 5 的数据并加记录锁 */
SELECT * FROM `test` WHERE `id` = 5 FOR UPDATE;
/* 延迟30秒执行，防止锁释放 */
SELECT SLEEP(30);

-- 注意：以下的语句不是放在一个事务中执行，而是分开多次执行，每次事务中只有一条添加语句

/* 事务2插入一条 name = '小张' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (4, '小张'); # 正常执行

/* 事务3插入一条 name = '小张' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (8, '小东'); # 正常执行

/* 提交事务1，释放事务1的锁 */
COMMIT;
```
以上，由于主键是唯一索引，而且是只使用一个索引查询，并且只锁定一条记录，所以，只会对 id = 5 的数据加上记录锁，而不会产生间隙锁。

#### 产生间隙锁

```sql
/* 开启事务1 */
BEGIN;
/* 查询 id 在 7 - 11 范围的数据并加记录锁 */
SELECT * FROM `test` WHERE `id` BETWEEN 5 AND 7 FOR UPDATE;
/* 延迟30秒执行，防止锁释放 */
SELECT SLEEP(30);

-- 注意：以下的语句不是放在一个事务中执行，而是分开多次执行，每次事务中只有一条添加语句

/* 事务2插入一条 id = 3，name = '小张1' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (3, '小张1'); # 正常执行

/* 事务3插入一条 id = 4，name = '小白' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (4, '小白'); # 正常执行

/* 事务4插入一条 id = 6，name = '小东' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (6, '小东'); # 阻塞

/* 事务5插入一条 id = 8， name = '大罗' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (8, '大罗'); # 阻塞

/* 事务6插入一条 id = 9， name = '大东' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (9, '大东'); # 阻塞

/* 事务7插入一条 id = 11， name = '李西' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (11, '李西'); # 阻塞

/* 事务8插入一条 id = 12， name = '张三' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (12, '张三'); # 正常执行

/* 提交事务1，释放事务1的锁 */
COMMIT;
```
从上面我们可以看到，(5, 7]、(7, 11] 这两个区间，都不可插入数据，其它区间，都可以正常插入数据。所以当我们给 (5, 7] 这个区间加锁的时候，会锁住 (5, 7]、(7, 11] 这两个区间。

#### 锁住不存在的数据

```sql
/* 开启事务1 */
BEGIN;
/* 查询 id = 3 这一条不存在的数据并加记录锁 */
SELECT * FROM `test` WHERE `id` = 3 FOR UPDATE;
/* 延迟30秒执行，防止锁释放 */
SELECT SLEEP(30);

-- 注意：以下的语句不是放在一个事务中执行，而是分开多次执行，每次事务中只有一条添加语句

/* 事务2插入一条 id = 3，name = '小张1' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (2, '小张1'); # 阻塞

/* 事务3插入一条 id = 4，name = '小白' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (4, '小白'); # 阻塞

/* 事务4插入一条 id = 6，name = '小东' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (6, '小东'); # 正常执行

/* 事务5插入一条 id = 8， name = '大罗' 的数据 */
INSERT INTO `test` (`id`, `name`) VALUES (8, '大罗'); # 正常执行

/* 提交事务1，释放事务1的锁 */
COMMIT;
```
我们可以看出，指定查询某一条记录时，如果这条记录不存在，会产生间隙锁

结论

- 对于指定查询某一条记录的加锁语句，如果该记录不存在，会产生记录锁和间隙锁，如果记录存在，则只会产生记录锁，如：WHERE `id` = 5 FOR UPDATE;
- 对于查找某一范围内的查询语句，会产生间隙锁，如：WHERE `id` BETWEEN 5 AND 7 FOR UPDATE;

### 普通索引的间隙锁

数据准备

创建 test1 表：

- 注意：number 不是唯一值

```sql
CREATE TABLE `test1` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `number` int(1) NOT NULL COMMENT '数字',
  PRIMARY KEY (`id`),
  KEY `number` (`number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

id 是主键，number上建立了一个普通索引。先加一些数据：

```sql
INSERT INTO `test1` VALUES (1, 1);
INSERT INTO `test1` VALUES (5, 3);
INSERT INTO `test1` VALUES (7, 8);
INSERT INTO `test1` VALUES (11, 12);
```

test1表中 number 索引存在的隐藏间隙：

(-infinity, 1]
(1, 3]
(3, 8]
(8, 12]
(12, +infinity]

#### 执行以下的事务（事务1最后提交）

```sql
/* 开启事务1 */
BEGIN;
/* 查询 number = 5 的数据并加记录锁 */
SELECT * FROM `test1` WHERE `number` = 3 FOR UPDATE;
/* 延迟30秒执行，防止锁释放 */
SELECT SLEEP(30);

-- 注意：以下的语句不是放在一个事务中执行，而是分开多次执行，每次事务中只有一条添加语句

/* 事务2插入一条 number = 0 的数据 */
INSERT INTO `test1` (`number`) VALUES (0); -- 正常执行

/* 事务3插入一条 number = 1 的数据 */
INSERT INTO `test1` (`number`) VALUES (1); -- 被阻塞

/* 事务4插入一条 number = 2 的数据 */
INSERT INTO `test1` (`number`) VALUES (2); -- 被阻塞

/* 事务5插入一条 number = 4 的数据 */
INSERT INTO `test1` (`number`) VALUES (4); -- 被阻塞

/* 事务6插入一条 number = 8 的数据 */
INSERT INTO `test1` (`number`) VALUES (8); -- 正常执行

/* 事务7插入一条 number = 9 的数据 */
INSERT INTO `test1` (`number`) VALUES (9); -- 正常执行

/* 事务8插入一条 number = 10 的数据 */
INSERT INTO `test1` (`number`) VALUES (10); -- 正常执行

/* 提交事务1 */
COMMIT;
```
这里可以看到，number (1 - 8) 的间隙中，插入语句都被阻塞了，而不在这个范围内的语句，正常执行，这就是因为有间隙锁的原因。

#### 加深对间隙锁的理解

将数据还原成初始化的那样

```sql
/* 开启事务1 */
BEGIN;
/* 查询 number = 5 的数据并加记录锁 */
SELECT * FROM `test1` WHERE `number` = 3 FOR UPDATE;
/* 延迟30秒执行，防止锁释放 */
SELECT SLEEP(30);

/* 事务1插入一条 id = 2， number = 1 的数据 */
INSERT INTO `test1` (`id`, `number`) VALUES (2, 1); -- 阻塞

/* 事务2插入一条 id = 3， number = 2 的数据 */
INSERT INTO `test1` (`id`, `number`) VALUES (3, 2); -- 阻塞

/* 事务3插入一条 id = 6， number = 8 的数据 */
INSERT INTO `test1` (`id`, `number`) VALUES (6, 8); -- 阻塞

/* 事务4插入一条 id = 8， number = 8 的数据 */
INSERT INTO `test1` (`id`, `number`) VALUES (8, 8); -- 正常执行

/* 事务5插入一条 id = 9， number = 9 的数据 */
INSERT INTO `test1` (`id`, `number`) VALUES (9, 9); -- 正常执行

/* 事务6插入一条 id = 10， number = 12 的数据 */
INSERT INTO `test1` (`id`, `number`) VALUES (10, 12); -- 正常执行

/* 事务7修改 id = 11， number = 12 的数据 */
UPDATE `test1` SET `number` = 5 WHERE `id` = 11 AND `number` = 12; -- 阻塞

/* 提交事务1 */
COMMIT;
```

这里有一个奇怪的现象：

事务3添加 id = 6，number = 8 的数据，给阻塞了；
事务4添加 id = 8，number = 8 的数据，正常执行了。
事务7将 id = 11，number = 12 的数据修改为 id = 11， number = 5的操作，给阻塞了；

这是为什么呢？我们来看看下边的图

![gap locks](https://tqlin.cn/upload/2019/11/gap%20locks-07b45f89a22d43afb7652b57733e8750.jpg)

从图中可以看出，当 number 相同时，会根据主键 id 来排序，所以：

事务3添加的 id = 6，number = 8，这条数据是在 （3, 8） 的区间里边，所以会被阻塞；
事务4添加的 id = 8，number = 8，这条数据则是在（8, 12）区间里边，所以不会被阻塞；
事务7的修改语句相当于在 （3, 8） 的区间里边插入一条数据，所以也被阻塞了。

#### 结论

- 在普通索引列上，不管是何种查询，只要加锁，都会产生间隙锁，这跟唯一索引不一样
- 在普通索引跟唯一索引中，数据间隙的分析，数据行是优先根据普通索引排序，再根据唯一索引排序

### 后码锁(Next-key Locks)

后码锁(，是记录锁与间隙锁的组合，它的封锁范围，既包含索引记录，又包含索引区间。

注：Next-key Locks的主要目的，也是为了避免幻读(Phantom Read)。如果把事务的隔离级别降级为RC，Next-key Locks则也会失效。

## 总结

- 记录锁、间隙锁、后码锁，都属于排它锁；
- 记录锁就是锁住一行记录；
- 间隙锁只有在事务隔离级别 RR 中才会产生；
- 唯一索引只有锁住多条记录或者一条不存在的记录的时候，才会产生间隙锁，指定给某条存在的记录加锁的时候，只会加记录锁，不会产生间隙锁；
- 普通索引不管是锁住单条，还是多条记录，都会产生间隙锁；
- 间隙锁会封锁该条记录相邻两个键之间的空白区域，防止其它事务在这个区域内插入、修改、删除数据，这是为了防止出现 幻读 现象；
- 普通索引的间隙，优先以普通索引排序，然后再根据主键索引排序；
- 事务级别是RC（读已提交）级别的话，间隙锁将会失效。

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
- [本文参考地址](https://zhuanlan.zhihu.com/p/48269420)