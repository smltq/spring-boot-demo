package demo.data.redis;

import demo.data.redis.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

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