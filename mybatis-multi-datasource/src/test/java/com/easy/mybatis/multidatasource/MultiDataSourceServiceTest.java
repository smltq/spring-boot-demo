package com.easy.mybatis.multidatasource;

import com.easy.mybatis.multidatasource.entity.User;
import com.easy.mybatis.multidatasource.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@Slf4j
//指定单元测试按字母顺序执行
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
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

        log.info("开始执行insert方法，id={}", user.getId());
        assertThat(userService.insert(user));
        // 成功直接拿会写的 ID
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void bUpdate() {
        User user = new User();
        user.setId(20l);
        user.setName("小羊update");
        user.setAge(3);
        user.setEmail("abc@mp.com");
        log.info("开始执行updateById方法，id={}", user.getId());
        assertThat(userService.updateById(user) > 0);
    }

    @Test
    public void cSelectById() {
        int id = 20;
        log.info("开始执行selectById方法，id={}", id);
        log.info("数据为=={}", userService.selectById(id));
    }

    @Test
    public void dDelete() {
        int id = 20;
        log.info("开始执行deleteById方法，id={}", id);
        assertThat(userService.deleteById(id));
    }


    @Test
    public void eSelectList() {
        for (int i = 0; i < 5; i++) {
            log.info("开始执行selectList方法，index={}", i);
            List<User> list = userService.selectList();
            log.info("查询到的数据为，list={}", list);
        }
    }
}
