package com.easy.mybatis.multidatasource;

import com.easy.mybatis.multidatasource.entity.User;
import com.easy.mybatis.multidatasource.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * 内置 CRUD 演示
 * </p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDataSourceServiceTest {

    @Resource
    private IUserService userService;

    @Test
    public void aInsert() {
        User user = new User();
        user.setId(20l);
        user.setName("小羊");
        user.setAge(3);
        user.setEmail("abc@mp.com");

        assertThat(userService.insert(user));
        // 成功直接拿会写的 ID
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void selectList() {
        List<User> list = userService.selectList();
        assertThat(!list.isEmpty());
    }
}
