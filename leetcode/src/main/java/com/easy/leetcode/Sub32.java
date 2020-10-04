package com.easy.leetcode;

/*
32. 最长有效括号
给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。

示例 1:

输入: "(()"
输出: 2
解释: 最长有效括号子串为 "()"
示例 2:

输入: ")()())"
输出: 4
解释: 最长有效括号子串为 "()()"
 */
public class Sub32 {
    public static void main(String[] args) {
        Solution_32_2 solution = new Solution_32_2();
        String s = ")()())";
        System.out.println("返回结果为：" + solution.longestValidParentheses(s));
    }
}

class Solution_32_2 {
    public int longestValidParentheses(String s) {
        return 0;
    }
}

/**
 * 动态规划
 * 状态转换方程
 * 1.前一个为'('的情况==>dp[i]=dp[i - 2] + 2
 * 2.前一个为')'的情况==>dp[i]=dp[i - 1] + dp[i - dp[i - 1] - 2] + 2
 */
class Solution_32_1 {
    public int longestValidParentheses(String s) {
        int len = s.length(), ans = 0;
        int[] dp = new int[len];
        for (int i = 1; i < len; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    if (i >= 2) {
                        dp[i] = dp[i - 2] + 2;
                    } else {
                        dp[i] = 2;
                    }
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    if (i - dp[i - 1] >= 2) {
                        dp[i] = dp[i - 1] + dp[i - dp[i - 1] - 2] + 2;
                    } else {
                        dp[i] = dp[i - 1] + 2;
                    }
                }
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }
}