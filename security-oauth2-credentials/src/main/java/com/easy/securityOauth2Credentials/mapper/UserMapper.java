package com.easy.securityOauth2Credentials.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.securityOauth2Credentials.entity.Permission;
import com.easy.securityOauth2Credentials.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ltq
 * @since 2019-08-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<Permission> queryUserAuthorities(Integer userId);
    User queryUserByUsername(String username);
}
