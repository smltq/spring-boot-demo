package com.easy.helloworld;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        List list = new ArrayList();
        while (true) {
            list.add(2048 * 1024 * 2048);
            list.add("为了撑爆内存也是没办法啦～");
        }
    }
}
