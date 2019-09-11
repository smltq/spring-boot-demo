# MySql事务

- MySQL 事务主要用于处理操作量大，复杂度高的数据。比如开单，需要添加给订单表增加记录，还需要增加订单的各种相关明细，操作复杂度高，这些操作语句需要构成一个事务。
- MySQL 命令行的默认设置，事务都是自动提交的，即执行 SQL 语句后就会马上执行 COMMIT 操作。因此要显式地开启一个事务务须使用命令 BEGIN 或 START TRANSACTION，或者执行命令 SET AUTOCOMMIT=0，用来禁止使用当前会话的自动提交。

## 事务

- 在 MySQL 中只有使用了 Innodb 数据库引擎的数据库或表才支持事务
- 事务处理可以用来维护数据库的完整性，保证成批的 SQL 语句要么全部执行，要么全部不执行。
- 一般来说，事务需要满足4个条件（ACID）：原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）、持久性（Durability）
- mysql默认是自动提交事务的

### 原子性

一个事务（transaction）中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。

### 一致性

在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设规则，这包含资料的精确度、串联性以及后续数据库可以自发性地完成预定的工作。

### 隔离性

数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。

### 持久性

事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

## 事务的隔离级别

隔离级别|脏读（Dirty Read）|不可重复读（NonRepeatable Read）|幻读（Phantom Read）
---|---|---|---
未提交读（Read uncommitted）|可能|可能|可能
已提交读（Read committed）|不可能|可能|可能
可重复读（Repeatable read）|不可能|不可能|可能
可串行化（Serializable ）|不可能|不可能|不可能

InnoDB默认是可重复读级别的

- ① 脏读: 脏读就是指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，这时，另外一个事务也访问这个数据，然后使用了这个数据。
- ② 不可重复读:是指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的的数据可能是不一样的。这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读。
- ③ 幻读:第一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行。同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。那么，以后就会发生操作第一个事务的用户发现表中还有没有修改的数据行，就好象发生了幻觉一样，幻读是数据行记录变多了或者少了。

简单点总结下他们的区别：脏读是指读取了未修改完的记录，不可重复读指因为被其它事务修改了记录导致某事务两次读取记录不一致，而幻读是指因为其它事务对表做了增删导致某事务两次读取的表记录数不一致问题。

### 第1级别未提交读(Read Uncommitted)

允许脏读，也就是可能读取到其他会话中未提交事务修改的数据

### 第2级别提交读(Read Committed)

只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别 (不重复读)

### 第3级别可重复读(Repeated Read)

可重复读。在同一个事务内的查询都是事务开始时刻一致的，InnoDB默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻象读

### 第4级别串行读(Serializable)

完全串行化的读，每次读都需要获得表级共享锁，读写相互都会阻塞

## mysql事务相关命令

### 查看mysql系统的事务隔离级别

```sql
mysql> SELECT @@global.tx_isolation;
```

### 查看mysql会话的事务隔离级别

```sql
mysql> SELECT @@tx_isolation;

-- 或

mysql> SELECT @@session.tx_isolation;

```

### 设置系统的事务隔离级别

```sql
mysql> set global transaction isolation level read committed;
```

### 设置会话的事务隔离级别

```sql
mysql> set session transaction isolation level read committed;  -- 值可以分别为:READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ, SERIALIZABLE
```

### 查看autocommit变量

1：表示自动提交事务，0表示不自动提交事务

```sql
mysql> select @@autocommit;
```

### 设置mysql不自动提交事务

```sql
mysql> set autocommit = 0;
```

### 事务回滚

```sql
mysql> rollback;
```

### 显示的开启一个事务

```sql
mysql> start transaction;

-- 或

mysql> begin;
```

### 创建一个保存点

```sql
mysql> savepoint tem1;
```

### 显示提交事务

```sql
mysql> commit;
```

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)
- [mysql官方文档](https://dev.mysql.com/doc/refman/8.0/en/innodb-transaction-isolation-levels.html)