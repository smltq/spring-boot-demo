package com.easy.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
77. 组合
给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。

你可以按 任何顺序 返回答案。

示例 1：

输入：n = 4, k = 2
输出：
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
示例 2：

输入：n = 1, k = 1
输出：[[1]]


提示：

1 <= n <= 20
1 <= k <= n
 */
public class Sub77 {
    public static void main(String[] args) {
        Solution_77 solution = new Solution_77();
        System.out.println("输出：" + solution.combine(4, 2));
    }
}

/**
 * 深度优先搜索
 */
class Solution_77 {
    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        dfs(n, k, 1, new LinkedList<>());
        return ans;
    }

    private void dfs(int n, int k, int cur, LinkedList<Integer> path) {
        // 递归结束条件
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }

        //剪枝：cur限制为：小于n - (k - path.size()) + 1
        for (int i = cur; i <= n - (k - path.size()) + 1; i++) {
            path.add(i);
            dfs(n, k, i + 1, path);
            //回溯
            path.removeLast();
        }
    }
}
