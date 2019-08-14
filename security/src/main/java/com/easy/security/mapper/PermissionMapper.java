package com.easy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.security.entity.RolePermisson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<RolePermisson> {

    @Select("SELECT A.NAME AS roleName,C.url FROM role AS A LEFT JOIN role_permission B ON A.id=B.role_id LEFT JOIN permission AS C ON B.permission_id=C.id")
    List<RolePermisson> getRolePermissions();
}
