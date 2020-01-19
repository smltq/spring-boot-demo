package com.easy.leetcode;

import java.util.Stack;

/*
给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

有效字符串需满足：

左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
注意空字符串可被认为是有效字符串。

示例 1:

输入: "()"
输出: true
示例 2:

输入: "()[]{}"
输出: true
示例 3:

输入: "(]"
输出: false
示例 4:

输入: "([)]"
输出: false
示例 5:

输入: "{[]}"
输出: true

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/valid-parentheses
 */
public class Sub20 {
    public static void main(String[] args) {
        Solution_20 solution = new Solution_20();
        String s = "([)]";
        Boolean result = solution.isValid(s);
        System.out.println("是否有效的字符串：" + result);
    }
}

class Solution_20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (getValue(c) != ' ') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                Character temp = stack.pop();
                if (getKey(c) != temp) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private Character getValue(Character key) {
        Character result;
        switch (key) {
            case '{':
                result = '}';
                break;
            case '[':
                result = ']';
                break;
            case '(':
                result = ')';
                break;
            default:
                result = ' ';
        }
        return result;
    }

    private Character getKey(Character val) {
        Character result;
        switch (val) {
            case '}':
                result = '{';
                break;
            case ']':
                result = '[';
                break;
            case ')':
                result = '(';
                break;
            default:
                result = ' ';
        }
        return result;
    }
}