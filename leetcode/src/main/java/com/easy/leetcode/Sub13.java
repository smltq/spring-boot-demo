package com.easy.leetcode;

import java.util.HashMap;
import java.util.Map;

/*
罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。

示例 1:

输入: "III"
输出: 3
示例 2:

输入: "IV"
输出: 4
示例 3:

输入: "IX"
输出: 9
示例 4:

输入: "LVIII"
输出: 58
解释: L = 50, V= 5, III = 3.
示例 5:

输入: "MCMXCIV"
输出: 1994
解释: M = 1000, CM = 900, XC = 90, IV = 4.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/roman-to-integer
 */
public class Sub13 {
    public static void main(String[] args) {
        Solution_13_2 solution = new Solution_13_2();
        String s = "IV";
        int result = solution.romanToInt(s);
        System.out.println(String.format("罗马数字：%s，表示整数：%s", s, result));
    }
}

/**
 * 思路
 * 首先将所有的组合可能性列出并添加到哈希表中
 * 然后对字符串进行遍历，由于组合只有两种，一种是 1 个字符，一种是 2 个字符，其中 2 个字符优先于 1 个字符
 * 先判断两个字符的组合在哈希表中是否存在，存在则将值取出加到结果 result 中，并向后移2个字符。不存在则将判断当前 1 个字符是否存在，存在则将值取出加到结果 result 中，并向后移 1 个字符
 * 遍历结束返回结果 result
 */
class Solution_13_1 {
    private Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("I", 1);
        put("V", 5);
        put("X", 10);
        put("L", 50);
        put("C", 100);
        put("D", 500);
        put("M", 1000);
        put("IV", 4);
        put("IX", 9);
        put("XL", 40);
        put("XC", 90);
        put("CD", 400);
        put("CM", 900);
    }};

    public int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); ) {
            //2个字符优先级高
            if (i + 1 < s.length() && map.get(s.substring(i, i + 2)) != null) {
                result += map.get(s.substring(i, i + 2));
                i += 2;
            } else {
                result += map.get(s.substring(i, i + 1));
                i++;
            }
        }
        return result;
    }
}

/**
 * 思路
 * 只要每次比较后一个和前一个的值的大小关系即可：
 * 前值小于后值，减去前值
 * 前值大于或等于后值，加上前值
 * 最后一个值必然是加上的
 */
class Solution_13_2 {
    private int getNum(char c) {
        int res = 0;
        switch (c) {
            case 'I':
                res = 1;
                break;
            case 'V':
                res = 5;
                break;
            case 'X':
                res = 10;
                break;
            case 'L':
                res = 50;
                break;
            case 'C':
                res = 100;
                break;
            case 'D':
                res = 500;
                break;
            case 'M':
                res = 1000;
                break;
            default:
                res = -1;
        }
        return res;
    }

    public int romanToInt(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }

        int res = 0;
        int pre = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int cur = getNum(s.charAt(i));
            if (cur >= pre) {
                res += cur;
            } else {
                res -= cur;
            }
            pre = cur;
        }
        return res;
    }
}