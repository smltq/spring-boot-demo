package com.easy.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
22. 括号生成
数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。



示例：

输入：n = 3
输出：[
       "((()))",
       "(()())",
       "(())()",
       "()(())",
       "()()()"
     ]
 */
public class Sub22 {
    public static void main(String[] args) {
        Solution_22 solution = new Solution_22();
        System.out.println("输出：" + solution.generateParenthesis(3));
    }
}

class Solution_22 {
    List<String> result = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        backtrack(n, n, new StringBuilder());
        return result;
    }

    public void backtrack(int open, int close, StringBuilder path) {
        if (close < open) return;

        if (open < 0 || close < 0) return;

        if (open == 0 && close == 0) {
            result.add(path.toString());
            return;
        }

        path.append("(");
        backtrack(open - 1, close, path);
        path.deleteCharAt(path.length() - 1);

        path.append(")");
        backtrack(open, close - 1, path);
        path.deleteCharAt(path.length() - 1);
    }
}