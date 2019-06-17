package demo.data.redis.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class UserController {

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