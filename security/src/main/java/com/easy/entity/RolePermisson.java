package com.easy.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RolePermisson {
    private String url;
    private String roleName;
}