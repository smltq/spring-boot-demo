package demo.data.mqRedis;

import demo.data.mqRedis.config.RedisMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class TestRedisMQ {

    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    @Test
    public void testMq() {
        String message = "Message " + UUID.randomUUID();
        redisMessagePublisher.publish(message);
    }
}
