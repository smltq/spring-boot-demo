package com.easy.securityOauth2Credentials.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.User;

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

}
