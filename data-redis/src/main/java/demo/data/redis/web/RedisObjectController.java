package demo.data.redis.web;

import demo.data.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/object")
public class RedisObjectController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @PutMapping("/addUser")
    public void addUser() {
        User user = new User("redisObject@126.com", "redisObject", "redisObject", "redisObject", "123");
        redisTemplate.opsForValue().set("addUser", user);
    }
}