package com.easy.leetcode;

import java.util.*;

/*
399. 除法求值
给出方程式 A / B = k, 其中 A 和 B 均为用字符串表示的变量， k 是一个浮点型数字。根据已知方程式求解问题，并返回计算结果。如果结果不存在，则返回 -1.0。

示例 :
给定 a / b = 2.0, b / c = 3.0
问题: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
返回 [6.0, 0.5, -1.0, 1.0, -1.0 ]

输入为: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries(方程式，方程式结果，问题方程式)， 其中 equations.size() == values.size()，即方程式的长度与方程式结果长度相等（程式与结果一一对应），并且结果值均为正数。以上为方程式的描述。 返回vector<double>类型。

基于上述例子，输入如下：

equations(方程式) = [ ["a", "b"], ["b", "c"] ],
values(方程式结果) = [2.0, 3.0],
queries(问题方程式) = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
输入总是有效的。你可以假设除法运算中不会出现除数为0的情况，且不存在任何矛盾的结果。
 */
public class Sub339 {
    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();

        List<String> e1 = new ArrayList<>();
        e1.add("a");
        e1.add("b");
        equations.add(e1);

        List<String> e2 = new ArrayList<>();
        e2.add("b");
        e2.add("c");
        equations.add(e2);

        double[] values = {2.0, 3.0};

        List<List<String>> queries = new ArrayList<>();

        List<String> q1 = new ArrayList<>();
        q1.add("a");
        q1.add("c");
        queries.add(q1);

        List<String> q2 = new ArrayList<>();
        q2.add("b");
        q2.add("a");
        queries.add(q2);

        List<String> q3 = new ArrayList<>();
        q3.add("a");
        q3.add("e");
        queries.add(q3);

        List<String> q4 = new ArrayList<>();
        q4.add("a");
        q4.add("a");
        queries.add(q4);

        List<String> q5 = new ArrayList<>();
        q5.add("x");
        q5.add("x");
        queries.add(q5);

        Solution_339 solution = new Solution_339();
        System.out.println("返回结果为：" + Arrays.toString(solution.calcEquation(equations, values, queries)));
    }
}

/**
 * 三指针解法
 */
class Solution_339 {
    //child parent
    Map<String, String> parents = new HashMap<>();
    //child mutiply of parent
    Map<String, Double> values = new HashMap<>();

    public void add(String x) {
        if (!parents.containsKey(x)) {
            parents.put(x, x);
            values.put(x, 1.0);
        }
    }

    public void union(String parent, String child, double value) {

        add(parent);
        add(child);
        String p1 = find(parent);
        String p2 = find(child);
        if (p1 == p2) {
            return;
        } else {
            parents.put(p2, p1);
            values.put(p2, value * (values.get(parent) / values.get(child)));
        }
    }

    public String find(String x) {
        while (parents.get(x) != x) {
            x = parents.get(x);
        }
        return x;
    }

    public double cal(String x) {
        // System.out.println("cal x"+x);
        double v = values.get(x);
        while (parents.get(x) != x) {
            x = parents.get(x);
            v *= values.get(x);
        }

        return v;
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {

        //union
        for (int i = 0; i < equations.size(); i++) {
            //union parent child value
            union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
        }

        //find
        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String c1 = queries.get(i).get(0);
            String c2 = queries.get(i).get(1);
            if (!parents.containsKey(c1) || !parents.containsKey(c2)) {
                ans[i] = -1;
                continue;
            }
            if (c1.equals(c2)) {
                ans[i] = 1;
                continue;
            }
            String p1 = find(c1);
            String p2 = find(c2);
            if (!p1.equals(p2)) {
                ans[i] = -1;
                continue;
            }
            ans[i] = cal(c2) / cal(c1);
        }
        return ans;
    }
}