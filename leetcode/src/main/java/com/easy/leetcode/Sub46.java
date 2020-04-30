package com.easy.leetcode;

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

class Solution_46 {
    public List<List<Integer>> permute(int[] nums) {
        return null;
    }
}