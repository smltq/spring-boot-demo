package com.easy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RolePermisson {
    @TableId
    private String roleName;
    @TableId
    private String url;
}