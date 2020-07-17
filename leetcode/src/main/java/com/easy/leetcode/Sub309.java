package com.easy.leetcode;

/*
309. 最佳买卖股票时机含冷冻期
给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
示例:

输入: [1,2,3,0,2]
输出: 3
解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 */
public class Sub309 {
    public static void main(String[] args) {
        Solution_309 solution = new Solution_309();
        int[] prices = {1, 2, 3, 0, 2};
        System.out.println("返回结果为：" + solution.maxProfit(prices));
    }
}

/**
 * 动态规划解法
 */
class Solution_309 {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len == 0) {
            return 0;
        }
        int dp[][] = new int[len][3];
        //dp[i][0] 手上有股票最大利润
        //dp[i][1] 手上无股票并且处于冷冻期最大利润
        //dp[i][2] 手上无股票并且不处于冷冻期最大利润
        dp[0][0] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
            dp[i][1] = dp[i - 1][0] + prices[i];
            dp[i][2] = Math.max(dp[i - 1][1], dp[i - 1][2]);
        }
        return Math.max(dp[len - 1][1], dp[len - 1][2]);
    }
}