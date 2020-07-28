package com.easy.java;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Java8之Stream
 */
public class Java8_Stream {
    public static void main(String[] args) {
        //Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。
        //Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
        //Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "love", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("过滤掉空字符串元素结果为：");
        filtered.forEach(System.out::println);

        //limit 方法用于获取指定数量的流。
        Random random = new Random();
        System.out.println("随机生成10个随机整数结果为：");
        random.ints().limit(10).forEach(System.out::println);

        //map 方法用于映射每个元素到对应的结果。
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("输出对应的平方数并且去重：");
        squaresList.forEach(System.out::println);

        //filter 方法用于通过设置的条件过滤出元素。
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("空字符串数量为：" + count);

        //sorted 方法用于对流进行排序。
        random.ints().limit(10).sorted().forEach(System.out::println);

        //parallelStream 是流并行处理程序的代替方法。
        long count2 = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("空字符串数量为：" + count2);

        //Collectors 类实现了很多归约操作
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);

        //统计
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }
}