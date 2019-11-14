# MySql锁

MySQL InnoDB支持三种行锁定

- 行锁（Record Lock）:锁直接加在索引记录上面，锁住的是key。

- 间隙锁（Gap Lock）:锁定索引记录间隙，确保索引记录的间隙不变。间隙锁是针对事务隔离级别为可重复读或以上级别而设计的。

- 后码锁（Next-Key Lock）：行锁和间隙锁组合起来就叫Next-Key Lock。

默认情况下，InnoDB工作在可重复读隔离级别下，并且会以Next-Key Lock的方式对数据行进行加锁，这样可以有效防止幻读的发生。Next-Key Lock是行锁和间隙锁的组合，当InnoDB扫描索引记录的时候，会首先对索引记录加上行锁（Record Lock），再对索引记录两边的间隙加上间隙锁（Gap Lock）。加上间隙锁之后，其他事务就不能在这个间隙修改或者插入记录。

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
- [mysql官方文档](https://dev.mysql.com/doc/refman/8.0/en/innodb-transaction-isolation-levels.html)