package com.easy.securityOauth2Credentials.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.securityOauth2Credentials.entity.Permission;
import com.easy.securityOauth2Credentials.entity.User;
import com.easy.securityOauth2Credentials.mapper.UserMapper;
import com.easy.securityOauth2Credentials.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ltq
 * @since 2019-08-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<Permission> queryUserAuthorities(Integer userId) {
        return baseMapper.queryUserAuthorities(userId);
    }

    @Override
    public User queryUserByUsername(String username) {
        return baseMapper.queryUserByUsername(username);
    }
}
