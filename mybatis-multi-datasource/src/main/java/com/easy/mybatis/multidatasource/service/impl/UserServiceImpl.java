package com.easy.mybatis.multidatasource.service.impl;

import com.easy.mybatis.multidatasource.annotation.Master;
import com.easy.mybatis.multidatasource.entity.User;
import com.easy.mybatis.multidatasource.mapper.UserMapper;
import com.easy.mybatis.multidatasource.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    @Override
    public int insert(User entity) {
        return userMapper.insert(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    @Override
    public int deleteById(Serializable id) {
        return userMapper.deleteById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    @Override
    public int updateById(User entity) {
        return userMapper.updateById(entity);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    @Master
    @Override
    public User selectById(Serializable id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> selectList() {
        return userMapper.selectList();
    }
}
