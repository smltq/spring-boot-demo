package com.easy.securityOauth2Credentials.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.securityOauth2Credentials.entity.Permission;
import com.easy.securityOauth2Credentials.mapper.PermissionMapper;
import com.easy.securityOauth2Credentials.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
