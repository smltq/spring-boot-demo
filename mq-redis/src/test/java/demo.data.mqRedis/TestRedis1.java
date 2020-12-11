package demo.data.mqRedis;

import demo.data.mqRedis.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
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
        assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("easy.demo", user);
        User userRedis = (User) redisTemplate.opsForValue().get("easy.demo");
        log.info(String.format("easy.demo值是：%s", userRedis.toString()));
    }

    @Test
    public void testMq() {
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForList().leftPush("task-queue", "data" + i);
            log.info("插入了一个新的任务==>{}", "data" + i);
        }
        String taskId = redisTemplate.opsForList().rightPop("task-queue").toString();
        log.info("处理成功，清除任务==>{}", taskId);
    }
}