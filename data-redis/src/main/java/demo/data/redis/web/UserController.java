package demo.data.redis.web;

import demo.data.redis.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @RequestMapping("/getUser")
    @Cacheable(value = "user-key")
    public User getUser() {
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody User user, HttpServletRequest request) {
//        request.getSession().setAttribute("user", user);
//        return new ResponseEntity(userRespository.save(user), HttpStatus.OK);
//    }
//
//    @GetMapping("/session")
//    public ResponseEntity getSessionMessage(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("sessionId", request.getSession().getId());
//        map.put("message", request.getSession().getAttribute("user"));
//        return new ResponseEntity(map, HttpStatus.OK);
//    }
}