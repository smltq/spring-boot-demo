package com.easy.easy.model;

import com.easy.easy.model.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.util.Date;


@RunWith(SpringRunner.class)
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

        Assert.assertEquals("bb2", userRepository.findByUserNameOrEmail("xx", "bb@qq.com").getNickName());
        userRepository.delete(userRepository.findByUserName("aa"));
    }
}