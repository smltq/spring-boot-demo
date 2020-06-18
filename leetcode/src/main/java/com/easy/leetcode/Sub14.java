package com.easy.leetcode;

/*
14. 最长公共前缀

编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 ""。

示例 1:

输入: ["flower","flow","flight"]
输出: "fl"
示例 2:

输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
说明:

所有输入只包含小写字母 a-z 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/longest-common-prefix
 */
public class Sub14 {
    public static void main(String[] args) {
        Solution_14 solution = new Solution_14();
        String[] strs = {"c", "acc", "ccc"};
        String result = solution.longestCommonPrefix(strs);
        System.out.println("最长公共前缀：" + result);
    }
}

/**
 * 水平扫描法
 */
class Solution_14 {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (prefix.isEmpty()) {
                break;
            }
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        return prefix;
    }
}
