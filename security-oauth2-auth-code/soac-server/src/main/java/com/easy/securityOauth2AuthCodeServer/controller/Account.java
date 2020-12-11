package com.easy.securityOauth2AuthCodeServer.controller;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "account")
@ToString(exclude = "fans")
@Builder
public class Account {

    private String name;
    private String nickName;
    private String remark;
    private List<Account> childAccount;
}
