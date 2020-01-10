package com.easy.leetcode;

import java.util.*;

/*

给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

示例:

输入: ["eat", "tea", "tan", "ate", "nat", "bat"],
输出:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
说明：

所有输入均为小写字母。
不考虑答案输出的顺序。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/group-anagrams
 */
public class Sub49 {
    public static void main(String[] args) {
        Solution_49 solution = new Solution_49();
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = solution.groupAnagrams(strs);
        for (List<String> str : result) {
            for (String subStr : str) {
                System.out.print(subStr);
            }
            System.out.println("");
        }
    }
}

class Solution_49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        for (String str : strs) {
            Map<Character, Integer> charMap = convertMap(str);
        }
        return result;
    }

    public Map convertMap(String str) {
        Map<Character, Integer> result = new HashMap();
        for (char ch : str.toCharArray()) {
            if (result.containsKey(ch)) {
                int val = result.get(ch);
                result.put(ch, val++);
            } else {
                result.put(ch, 1);
            }
        }
        return result;
    }
}