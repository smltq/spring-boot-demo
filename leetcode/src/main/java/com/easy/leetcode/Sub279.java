package com.easy.leetcode;

/*
279. 完全平方数
给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。

示例 1:

输入: n = 12
输出: 3
解释: 12 = 4 + 4 + 4.
示例 2:

输入: n = 13
输出: 2
解释: 13 = 4 + 9.
 */
public class Sub279 {
    public static void main(String[] args) {
        int n = 12;
        Solution_279_1 solution = new Solution_279_1();
        System.out.println("返回结果为：" + solution.numSquares(n));
    }
}

/**
 * 动态规划
 * 动态转移方程为：dp[i] = min(dp[i], dp[i - j * j] + 1)，i表示当前数字，j*j表示平方数
 */
class Solution_279_1 {
    public int numSquares(int n) {
        int dp[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; i - j * j >= 0; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}

/**
 * 数学公式
 * 四平方和定理，百度百科：https://baike.baidu.com/item/%E5%9B%9B%E5%B9%B3%E6%96%B9%E5%92%8C%E5%AE%9A%E7%90%86
 * 1.首先，我们检查数字 n 的形式是否为 n = 4^k(8m+1)，如果是，则直接返回 4
 * 2.否则，我们进一步检查这个数本身是否是一个完全平方数，或者这个数是否可以分解为两个完全平方数和
 * 3.在底部的情况下，这个数可以分解为 3 个平方和，但我们也可以根据四平方定理，通过加零，把它分解为 4 个平方。但是我们被要求找出最小的平方数
 */
class Solution_279_2 {
    protected boolean isSquare(int n) {
        int sq = (int) Math.sqrt(n);
        return n == sq * sq;
    }

    public int numSquares(int n) {
        //1.检查是否满足公式4^k(8m+1)
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }
        //2.检查是否完全平方数
        if (isSquare(n)) {
            return 1;
        }
        //检查是否可以分解为两个完全平方数和
        for (int i = 1; i * i <= n; ++i) {
            if (this.isSquare(n - i * i))
                return 2;
        }
        //其它情况表示这个数可以分解为3个平方和
        return 3;
    }
}