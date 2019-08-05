package com.easy.easy.web;

import com.easy.easy.model.User;
import com.easy.easy.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getUser")
    public User getUser(HttpServletRequest request) {
        User user = userRepository.findByUserName("myName");
        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(user.getId().toString(), user.getNickName());
        }
        return user;
    }

    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}