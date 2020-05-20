package com.easy.leetcode;

/*
96. 不同的二叉搜索树
给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？

示例:

输入: 3
输出: 5
解释:
给定 n = 3, 一共有 5 种不同结构的二叉搜索树:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
 */

public class Sub96 {
    public static void main(String[] args) {
        Solution_96 solution = new Solution_96();
        System.out.println("输出：" + solution.numTrees(3));
    }
}

/**
 * 卡塔兰数
 * C0=1
 * Cn+1=Cn*2*(2*n+1)/(n+2)
 */
class Solution_96 {
    public int numTrees(int n) {
        long c = 1;
        for (int i = 0; i < n; ++i) {
            c = c * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) c;
    }
}