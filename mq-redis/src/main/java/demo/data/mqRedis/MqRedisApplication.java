package demo.data.mqRedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MqRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqRedisApplication.class, args);
    }
}