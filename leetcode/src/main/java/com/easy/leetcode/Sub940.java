package com.easy.leetcode;

/*
940. 不同的子序列 II
给定一个字符串 S，计算 S 的不同非空子序列的个数。

因为结果可能很大，所以返回答案模 10^9 + 7.



示例 1：

输入："abc"
输出：7
解释：7 个不同的子序列分别是 "a", "b", "c", "ab", "ac", "bc", 以及 "abc"。
示例 2：

输入："aba"
输出：6
解释：6 个不同的子序列分别是 "a", "b", "ab", "ba", "aa" 以及 "aba"。
示例 3：

输入："aaa"
输出：3
解释：3 个不同的子序列分别是 "a", "aa" 以及 "aaa"。




提示：

S 只包含小写字母。
1 <= S.length <= 2000
 */
public class Sub940 {
    public static void main(String[] args) {
        String s = "abc";
        Solution_940 solution = new Solution_940();
        System.out.println("返回结果为：" + solution.distinctSubseqII(s));
    }
}

class Solution_940 {
    public int distinctSubseqII(String S) {
        return 0;
    }
}