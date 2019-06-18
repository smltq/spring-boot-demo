# redis使用示例

## 主要内容

- 使用lettuce操作redis
- redis字符串存储(RedisStringController.java)
- redis对象存储(RedisObjectController.java)
- 对象存储自定义序列化(RedisConfig.java)
- lettuce 连接池配置(application.yml)
- 启用session redis(SessionConfig.java)
- YAML配置语言(application.yml)

## 介绍

Redis 是目前业界使用最广泛的内存数据存储。相比 Memcached，Redis 支持更丰富的数据结构，例如 hashes, lists, sets 等，同时支持数据持久化。除此之外，Redis 还提供一些类数据库的特性，比如事务，HA，主从库。可以说 Redis 兼具了缓存系统和数据库的一些特性，因此有着丰富的应用场景。本文介绍 Redis 在 Spring Boot 中两个典型的应用场景。

## 如何使用

```xml
    <!-- redis 缓存 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!-- session 缓存 -->
    <dependency>
        <groupId>org.springframework.session</groupId>
        <artifactId>spring-session-data-redis</artifactId>
    </dependency>
    <!-- lettuce pool 缓存连接池 -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
```

Spring Boot 提供了对 Redis 集成的组件包：spring-boot-starter-data-redis，spring-boot-starter-data-redis依赖于spring-data-redis 和 lettuce

Lettuce 是一个可伸缩线程安全的 Redis 客户端，多个线程可以共享同一个 RedisConnection，它利用优秀 netty NIO 框架来高效地管理多个连接。

## 添加yml配置文件

```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    password:
    lettuce:
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: 1000
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池中的最小空闲连接
        min-idle: 0
        # 关闭超时时间
        shutdown-timeout: 100
```

## 添加 cache 的配置类

```java
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类,方便调试redis
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //使用StringRedisSerializer来序列化和反序列化redis的ke
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //开启事务
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    /**
     * 自定义生成key的策略
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getSimpleName() + "_"
                        + method.getName() + "_"
                        + StringUtils.arrayToDelimitedString(params, "_");
            }
        };
    }
}
```

当不指定缓存的key时，SpringBoot会使用SimpleKeyGenerator生成key

问题：
    如果2个方法，参数是一样的，但执行逻辑不同，那么将会导致执行第二个方法时命中第一个方法的缓存。
解决办法
    是在@Cacheable注解参数中指定key，或者自己实现一个KeyGenerator，在注解中指定KeyGenerator。

但是如果这样的情况很多，每一个都要指定key、KeyGenerator很麻烦。Spring同样提供了方案：继承CachingConfigurerSupport并重写keyGenerator()

## 使用示例代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRedis1 {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        log.info(String.format("aaa值是：%s", stringRedisTemplate.opsForValue().get("aaa")));
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("easy.demo", user);
        User userRedis = (User) redisTemplate.opsForValue().get("easy.demo");
        log.info(String.format("easy.demo值是：%s", userRedis.toString()));
    }
}
```

## 共享 Session

分布式系统中，Session 共享有很多的解决方案，其中托管到缓存中应该是最常用的方案之一。

Spring Session 提供了一套创建和管理 Servlet HttpSession 的方案。Spring Session 提供了集群 Session（Clustered Sessions）功能，默认采用外置的 Redis 来存储 Session 数据，以此来解决 Session 共享的问题。

### session启用配置

```java
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class SessionConfig {
}
```

maxInactiveIntervalInSeconds: 设置 Session 失效时间，使用 Redis Session 之后，原 Spring Boot 的 server.session.timeout 属性不再生效。

### 使用示例代码

```java
    @RequestMapping("/uid")
    public String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
```

调用以上方法后，打开redis查看，会发现已经把session存入redis了

## 资料

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/data-redis/HELP.md)
- [redis下载地址](https://github.com/microsoftarchive/redis/releases)
- [redis管理工具](https://github.com/necan/RedisDesktopManager-Windows/releases)