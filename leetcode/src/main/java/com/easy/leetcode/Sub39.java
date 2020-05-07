package com.easy.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
39. 组合总和
给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。

candidates 中的数字可以无限制重复被选取。

说明：

所有数字（包括 target）都是正整数。
解集不能包含重复的组合。
示例 1:

输入: candidates = [2,3,6,7], target = 7,
所求解集为:
[
  [7],
  [2,2,3]
]
示例 2:

输入: candidates = [2,3,5], target = 8,
所求解集为:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]
 */
public class Sub39 {
    public static void main(String[] args) {
        int[] candidates = new int[]{2, 3, 5};
        Solution_39 solution = new Solution_39();
        System.out.println("输出：" + solution.combinationSum(candidates, 8));
    }
}

/**
 * 递归-回溯算法
 */
class Solution_39 {
    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //使数组升序
        Arrays.sort(candidates);

        dfs(candidates, 0, target, new ArrayList<>());
        return ans;
    }

    public void dfs(int[] candidates, int start, int target, List<Integer> path) {
        //剪枝
        if (target < 0) {
            return;
        }

        if (target == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            //剪枝
            if (candidates[i] > target) {
                break;
            }
            path.add(candidates[i]);
            dfs(candidates, i, target - candidates[i], path);
            path.remove(path.size() - 1);
        }
    }
}