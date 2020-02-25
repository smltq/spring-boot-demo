package com.easy.leetcode;

/*
70. 爬楼梯
假设你正在爬楼梯。需要 n 阶你才能到达楼顶。

每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

注意：给定 n 是一个正整数。

示例 1：

输入： 2
输出： 2
解释： 有两种方法可以爬到楼顶。
1.  1 阶 + 1 阶
2.  2 阶

示例 2：

输入： 3
输出： 3
解释： 有三种方法可以爬到楼顶。
1.  1 阶 + 1 阶 + 1 阶
2.  1 阶 + 2 阶
3.  2 阶 + 1 阶
 */
public class Sub70 {
    public static void main(String[] args) {
        Solution_70 solution = new Solution_70();
        int result = solution.climbStairs(1);
        System.out.println("返回结果为：" + result);
    }
}

/*

动态规划

不难发现，这个问题可以被分解为一些包含最优子结构的子问题，即它的最优解可以从其子问题的最优解来有效地构建，我们可以使用动态规划来解决这一问题。

第 i 阶可以由以下两种方法得到：

在第 (i−1) 阶后向上爬一阶。

在第 (i−2) 阶后向上爬 22 阶。

所以到达第 i 阶的方法总数就是到第 (i−1) 阶和第 (i−2) 阶的方法数之和。

令 dp[i] 表示能到达第 ii 阶的方法总数：

dp[i]=dp[i-1]+dp[i-2]

 */
class Solution_70 {
    public int climbStairs(int n) {
        if (n == 1) return 1;
        int first = 1, second = 2, third = 0;
        for (int i = 3; i <= n; i++) {
            third = first + second;
            first = second;
            second = third;
        }
        return second;
    }
}