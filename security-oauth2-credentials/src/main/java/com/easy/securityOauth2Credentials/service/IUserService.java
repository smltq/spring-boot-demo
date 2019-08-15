package com.easy.securityOauth2Credentials.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.securityOauth2Credentials.entity.Permission;
import com.easy.securityOauth2Credentials.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ltq
 * @since 2019-08-14
 */
public interface IUserService extends IService<User> {
    List<Permission> queryUserAuthorities(Integer userId);

    User queryUserByUsername(String username);
}
