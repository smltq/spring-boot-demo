package com.easy.mybatis.multidatasource.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.mybatis.multidatasource.entity.User;

/**
 * <p>
 * MP 支持不需要 UserMapper.xml 这个模块演示内置 CRUD 咱们就不要 XML 部分了
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {

}
