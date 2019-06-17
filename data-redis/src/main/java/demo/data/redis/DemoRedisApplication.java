package demo.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootApplication
public class DemoRedisApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void run(String... args) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String key = "DemoRedisApplication";
        if (!this.template.hasKey(key)) {
            ops.set(key, "easy-web");
        }
        System.out.println("找到key=" + key + ", 值=" + ops.get(key));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoRedisApplication.class, args).close();
    }

    @SpringBootApplication
    public static class DemoRedisApplication1 implements CommandLineRunner {

        @Autowired
        private StringRedisTemplate template;

        @Override
        public void run(String... args) {
            ValueOperations<String, String> ops = this.template.opsForValue();
            String key = "DemoRedisApplication";
            if (!this.template.hasKey(key)) {
                ops.set(key, "easy-web");
            }
            System.out.println("找到key=" + key + ", 值=" + ops.get(key));
        }

        public static void main(String[] args) {
            SpringApplication.run(DemoRedisApplication1.class, args).close();
        }
    }
}
