package com.easy.securityOauth2AuthCodeServer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {

    public static Map<String, Account> database;

    static {
        database = new HashMap<>();
        database.put("testAccount1", Account.builder().name("testAccount1").nickName("测试用户1").remark("备注1").build());
        database.put("testAccount2", Account.builder().name("testAccount1").nickName("测试用户2").remark("备注2").build());

        Account qqAccount1 = database.get("testAccount1");
        qqAccount1.setChildAccount(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            qqAccount1.getChildAccount().add(Account.builder().name("testChild1_" + i).nickName("测试子用户1_" + i).remark(i + "").build());
        }

        Account qqAccount2 = database.get("testAccount2");
        qqAccount2.setChildAccount(new ArrayList<>());
        for (int i = 0; i < 7; i++) {
            qqAccount2.getChildAccount().add(Account.builder().name("testChild2_" + i).nickName("测试子用户2_" + i).remark(i + "").build());
        }
    }
}
