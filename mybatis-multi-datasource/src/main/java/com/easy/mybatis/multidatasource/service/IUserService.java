package com.easy.mybatis.multidatasource.service;

import com.easy.mybatis.multidatasource.annotation.Slave;
import com.easy.mybatis.multidatasource.entity.User;

import java.io.Serializable;
import java.util.List;

public interface IUserService {
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    @Slave
    int insert(User entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    int deleteById(Serializable id);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    int updateById(User entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    User selectById(Serializable id);

    List<User> selectList();
}
