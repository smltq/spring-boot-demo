package com.easy.leetcode;

import java.util.Stack;

/*

394. 字符串解码
给定一个经过编码的字符串，返回它解码后的字符串。

编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。

你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。

此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。



示例 1：

输入：s = "3[a]2[bc]"
输出："aaabcbc"
示例 2：

输入：s = "3[a2[c]]"
输出："accaccacc"
示例 3：

输入：s = "2[abc]3[cd]ef"
输出："abcabccdcdcdef"
示例 4：

输入：s = "abc3[cd]xyz"
输出："abccdcdcdxyz"
 */
public class Sub394 {
    public static void main(String[] args) {
        String s = "100[leetcode]";
        Solution_394 solution = new Solution_394();
        System.out.println("返回结果：" + solution.decodeString(s));
    }
}

/**
 * 堆栈解法
 * 遍历字符串
 * 1.遇到数值，入数值栈
 * 2.遇到字母或左中括号，入字母栈
 * 3.否则就是遇到右中括号：首先字母栈出栈操作，拼接为字符串后，按数字栈次数回入字母栈，直到字符串s遍历完成
 * 4.最后拼接成为结果字符串，因为是栈操作，最后拼成的字符串记得反转
 */
class Solution_394 {
    public String decodeString(String s) {
        Stack<String> letterStack = new Stack<>();
        Stack<Integer> numStack = new Stack<>();
        StringBuffer ans = new StringBuffer();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; ) {
            if (Character.isDigit(chars[i])) {
                String strNum = "";
                do {
                    strNum += chars[i];
                } while (Character.isDigit(chars[++i]));
                numStack.push(Integer.parseInt(strNum));
            } else if (Character.isLetter(chars[i]) || chars[i] == '[') {
                letterStack.push(Character.toString(chars[i]));
                i++;
            } else if (chars[i] == ']') {
                String cc = letterStack.pop();
                StringBuffer t = new StringBuffer();
                do {
                    t.append(cc);
                    cc = letterStack.pop();
                } while (!cc.equals("[") && !letterStack.isEmpty());
                int len = numStack.pop();
                for (int ii = 0; ii < len; ii++) {
                    letterStack.push(t.toString());
                }
                i++;
            }
        }
        while (!letterStack.isEmpty()) {
            ans.append(letterStack.pop());
        }
        return ans.reverse().toString();
    }
}