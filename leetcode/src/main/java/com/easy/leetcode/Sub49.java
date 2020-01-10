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
        Solution_49_2 solution = new Solution_49_2();
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = solution.groupAnagrams(strs);
        for (List<String> str : result) {
            for (String subStr : str) {
                System.out.print(subStr + ",");
            }
            System.out.println();
        }
    }
}

class Solution_49_1 {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList();
        Map<String, List> resultMap = new HashMap<>();
        int[] count = new int[26];
        for (String str : strs) {
            //全部填0
            Arrays.fill(count, 0);

            //填充字典值
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }

            //根据字典生成key
            StringBuilder mapKey = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                mapKey.append(count[i]);
            }
            String key = mapKey.toString();

            //初始化list
            if (!resultMap.containsKey(key)) {
                resultMap.put(key, new ArrayList());
            }
            //添加list值
            resultMap.get(key).add(str);
        }
        return new ArrayList(resultMap.values());
    }
}

class Solution_49_2 {
    public List<List<String>> groupAnagrams(String[] strs) {
        int[] table = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        HashMap<Long, List<String>> map = new HashMap<>();

        for (String str : strs) {
            long sum = 1L;
            for (char c : str.toCharArray()) {
                sum *= table[c - 'a'];
            }

            List<String> list = map.get(sum);
            if (null == list) {
                list = new ArrayList<>();
                map.put(sum, list);
            }
            list.add(str);
        }

        return new ArrayList(map.values());
    }
}