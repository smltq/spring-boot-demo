package com.easy.leetcode;

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

class Solution_39 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        return null;
    }
}