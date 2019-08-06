package com.easy.endpoint;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "qq")
@ToString(exclude = "fans")
@Builder
public class QQAccount {

    private String qq;
    private String nickName;
    private String level;
    private List<QQAccount> fans;

}
