# Java并发编程之ThreadLocal解析

![](ThreadLocal.png)

本文讨论的是JDK 1.8中的ThreadLocal

## ThreadLocal概念

ThreadLocal多线程间并发访问变量的解决方案，为每个线程提供变量的副本，用空间换时间。

- ThreadLocal在每个线程中对该变量会创建一个副本，即每个线程内部都会有一个该变量，且在线程内部任何地方都可以使用，线程之间互不影响，实现线程隔离，这样一来就不存在线程安全问题，也不会严重影响程序执行性能
- 由于在每个线程中都创建了副本，所以要考虑它对资源的消耗，比如内存的占用会比不使用ThreadLocal要大
- 如果使用ThreadLocal，通常定义为private static类型，在我看来最好是定义为private static final类型

## ThreadLocal使用场景

个人认为只要满足以下两点需求，就可以考虑使用ThreadLocal

- 每个线程需要有自己单独的实例
- 实例需要在多个方法中共享，但不希望被多线程共享

比如：创建数据库连接，在多线程情况下，我们肯定不希望出现A线程拿到连接未执行完，B线程就把它关闭或多个线程共用一个连接导致数据操作混乱等情况。而我们正确的姿势应该会撸上以下这样的类似代码：

```java
private static ThreadLocal<Connection> connTl = new ThreadLocal<>();

public static Connection getConnection() throws SQLException{
    Connection conn = connTl.get();
    if(conn==null){
        conn = dataSource.getConnection();
        connTl.set(conn);
    }
    return conn;
}
```

## ThreadLocal常用方法介绍

```java
class ThreadLocal<T> {
    T initValue();
    T get();
    void set(T value);
    void remove();
    static ThreadLocal<T> withInitial(Supplier<T> supplier);
}
```

### initValue

```java
public T initValue();
```


### 设置当前线程的线程局部变量的值

```java
public void set(T value);
```

### 返回当前线程所对应的线程局部变量

```java
public T get();
```

### 将当前线程局部变量的值删除，目的是为了减少内存的占用。需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。

```java
public void remove()
```

protected Object initialValue()

返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省实现直接返回一个null。

