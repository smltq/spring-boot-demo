package com.easy.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        Solution_32 solution = new Solution_32();
        String s = "(()";
        System.out.println("返回结果为：" + solution.longestValidParentheses(s));
    }
}

class Solution_32 {
    public int longestValidParentheses(String s) {
        return 0;
    }
}
