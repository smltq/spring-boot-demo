package com.easy.leetcode;

/*

判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

示例 1:

输入: 121
输出: true
示例 2:

输入: -121
输出: false
解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
示例 3:

输入: 10
输出: false
解释: 从右向左读, 为 01 。因此它不是一个回文数。
进阶:

你能不将整数转为字符串来解决这个问题吗？

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/palindrome-number
 */
public class Sub9 {
    public static void main(String[] args) {
        Solution_9 solution = new Solution_9();
        int x = 121;
        boolean result = solution.isPalindrome(x);
        System.out.println("是否回文数：" + result);
    }
}

class Solution_9 {
    public boolean isPalindrome(int x) {
        int result = 0, i = 0, t = x;
        while (x > 0) {
            result = x % 10 + result * i * 10;
            i++;
            x = x / 10;
        }
        System.out.println("result：" + result);
        if (x == result) {
            return true;
        }
        return false;
    }
}
