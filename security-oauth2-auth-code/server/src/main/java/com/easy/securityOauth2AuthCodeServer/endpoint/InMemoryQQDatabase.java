package com.easy.securityOauth2AuthCodeServer.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryQQDatabase {

    public static Map<String, QQAccount> database;

    static {
        database = new HashMap<>();
        database.put("271657370", QQAccount.builder().qq("271657370").nickName("云天").level("54").build());
        database.put("1246662770", QQAccount.builder().qq("1246662770").nickName("未命名").level("31").build());

        QQAccount qqAccount1 = database.get("271657370");
        qqAccount1.setFans(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            qqAccount1.getFans().add(QQAccount.builder().qq("1000000" + i).nickName("fan" + i).level(i + "").build());
        }

        QQAccount qqAccount2 = database.get("1246662770");
        qqAccount2.setFans(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            qqAccount2.getFans().add(QQAccount.builder().qq("2000000" + i).nickName("fan" + i).level(i + "").build());
        }
    }
}
