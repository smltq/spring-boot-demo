package com.easy.securityOauth2Credentials.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.securityOauth2Credentials.entity.Role;
import com.easy.securityOauth2Credentials.mapper.RoleMapper;
import com.easy.securityOauth2Credentials.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ltq
 * @since 2019-08-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
