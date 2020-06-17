package com.easy.leetcode;

/*
62. 不同路径
一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。

问总共有多少条不同的路径？



例如，上图是一个7 x 3 的网格。有多少可能的路径？
 */
public class Sub62 {
    public static void main(String[] args) {
        int m = 7, n = 3;
        Solution_62_1 solution = new Solution_62_1();
        System.out.println("返回结果为：" + solution.uniquePaths(m, n));
    }
}

/**
 * 暴力解法
 */
class Solution_62_1 {
    public int uniquePaths(int m, int n) {
        return 0;
    }
}