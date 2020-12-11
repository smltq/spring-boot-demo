package com.easy.web.model;

import com.easy.web.model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.DateFormat;
import java.util.Date;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);

        userRepository.save(new User("aa1", "aa@qq.com", "aa", "aa123456", formattedDate));
        userRepository.save(new User("bb2", "bb@qq.com", "bb", "bb123456", formattedDate));
        userRepository.save(new User("cc3", "cc@qq.com", "cc", "cc123456", formattedDate));

        Assertions.assertEquals("bb2", userRepository.findByUserNameOrEmail("xx", "bb@qq.com").getNickName());
        userRepository.delete(userRepository.findByUserName("aa"));
    }
}