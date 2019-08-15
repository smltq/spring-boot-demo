package com.easy.securityOauth2Credentials.base;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BaseEntity {
    @TableId
    Integer id;
}
