package com.easy.leetcode;

/*
647. 回文子串
给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。

具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。

示例 1:

输入: "abc"
输出: 3
解释: 三个回文子串: "a", "b", "c".
示例 2:

输入: "aaa"
输出: 6
说明: 6个回文子串: "a", "a", "a", "aa", "aa", "aaa".
注意:

输入的字符串长度不会超过1000。
 */
public class Sub647 {
    public static void main(String[] args) {
        String s = "aaa";
        Solution_647_1 solution = new Solution_647_1();
        System.out.println("返回结果为：" + solution.countSubstrings(s));
    }
}


class Solution_647_1 {
    public int countSubstrings(String s) {
        int ans = 0, n = s.length();
        for (int i = 0; i < n; i++) {
            int left = i;
            int right = left + i % 2;
            System.out.println(String.format("left=%s,right=%s,s[left]=%s,s[left]=%s", left, right, s.charAt(left), s.charAt(right)));
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                ans++;
            }
        }
        return ans;
    }
}