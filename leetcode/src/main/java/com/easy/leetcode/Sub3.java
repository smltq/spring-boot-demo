package com.easy.leetcode;

import java.util.HashMap;
import java.util.Map;

/*
3. 无重复字符的最长子串
给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

示例 1:

输入: "abcabcbb"
输出: 3
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
示例 2:

输入: "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
示例 3:

输入: "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
*/
public class Sub3 {
    public static void main(String[] args) {
        Solution_3 solution = new Solution_3();
        String s = "pwwkew";
        int ans = solution.lengthOfLongestSubstring(s);
        System.out.println("结果：" + ans);
    }
}

/**
 * 利用 双指针+哈希 实现
 */
class Solution_3 {
    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length, ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; i < len; i++) {
            if (map.containsKey(chars[i])) {
                int t = map.get(chars[i]);
                while (j <= t) {
                    map.remove(chars[j++]);
                }
            }
            map.put(chars[i], i);
            ans = Math.max(ans, map.size());
        }
        return ans;
    }
}