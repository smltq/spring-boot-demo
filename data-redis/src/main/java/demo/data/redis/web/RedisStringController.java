package demo.data.redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/string")
public class RedisStringController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping(value = "/put")
    public void put(String key, @RequestParam(required = false, defaultValue = "default") String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @GetMapping(value = "/get")
    public Object get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}