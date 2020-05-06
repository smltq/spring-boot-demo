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

/**
 * 回溯法-递归实现
 */
class Solution_22 {
    List<String> result = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        find(0, 0, n, new StringBuilder());
        return result;
    }

    public void find(int open, int close, int n, StringBuilder path) {
        //剪枝，开括号比闭括号少
        if (open < close) return;

        //剪枝，开括号或闭括号数量超过n
        if (open > n || close > n) return;

        //开闭括号数量刚好等于n，满足条件，保存到结果列表
        if (open == n && close == n) {
            result.add(path.toString());
            return;
        }

        //添加开扣号
        path.append("(");
        find(open + 1, close, n, path);
        //撤销开扣号
        path.deleteCharAt(path.length() - 1);

        //添加右括号
        path.append(")");
        find(open, close + 1, n, path);
        //撤销右括号
        path.deleteCharAt(path.length() - 1);
    }
}