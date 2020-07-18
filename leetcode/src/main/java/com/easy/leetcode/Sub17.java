package com.easy.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
17. 电话号码的字母组合
给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。



示例:

输入："23"
输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
说明:
尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
 */
public class Sub17 {
    public static void main(String[] args) {
        Solution_17 solution = new Solution_17();
        System.out.println("返回结果为：" + solution.letterCombinations("23"));
    }
}

class Solution_17 {
    Map<Character, String> map = new HashMap<Character, String>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};
    List<String> ans = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return ans;
        }
        findStr(digits, 0, "");
        return ans;
    }

    private void findStr(String digits, int index, String str) {
        if (index == digits.length()) {
            ans.add(str);
            return;
        }

        String strS = map.get(digits.charAt(index));
        for (Character c : strS.toCharArray()) {
            findStr(digits, index + 1, str + c);
        }
    }
}