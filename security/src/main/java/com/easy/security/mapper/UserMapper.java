package com.easy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.security.entity.Role;
import com.easy.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<Role> {

    @Select("select id , username , password from user where username = #{username}")
    User loadUserByUsername(@Param("username") String username);
}
