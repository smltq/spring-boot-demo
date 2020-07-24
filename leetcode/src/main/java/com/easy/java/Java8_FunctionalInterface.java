package com.easy.java;

import java.util.Arrays;
import java.util.List;

/**
 * Java8之函数式接口
 */
public class Java8_FunctionalInterface {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println("输出所有数据:");
        foreach(list, n -> true);

        System.out.println("输出所有偶数:");
        foreach(list, n -> n % 2 == 0);

        System.out.println("输出大于 5 的所有数字:");
        foreach(list, n -> n > 5);
    }

    static void foreach(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }

    //函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
    //函数式接口可以被隐式转换为 lambda 表达式。
    @FunctionalInterface
    interface Predicate<T> {
        boolean test(T t);
    }
}