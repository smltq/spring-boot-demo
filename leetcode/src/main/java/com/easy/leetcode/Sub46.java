package com.easy.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
46. 全排列
给定一个 没有重复 数字的序列，返回其所有可能的全排列。

示例:

输入: [1,2,3]
输出:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
 */
public class Sub46 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        Solution_46 solution = new Solution_46();
        System.out.println("输出：" + solution.permute(nums));
    }
}

/**
 * 递归-深搜
 */
class Solution_46 {
    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) return ans;
        boolean used[] = new boolean[nums.length];
        dfs(nums, 0, used);
        return ans;
    }

    public void dfs(int[] nums, int depth, boolean[] used) {
        if (depth == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;

                dfs(nums, depth + 1, used);

                used[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}