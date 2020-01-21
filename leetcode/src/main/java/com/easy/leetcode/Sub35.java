package com.easy.leetcode;

/*
给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。

你可以假设数组中无重复元素。

示例 1:

输入: [1,3,5,6], 5
输出: 2
示例 2:

输入: [1,3,5,6], 2
输出: 1
示例 3:

输入: [1,3,5,6], 7
输出: 4
示例 4:

输入: [1,3,5,6], 0
输出: 0

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/search-insert-position
 */
public class Sub35 {
    public static void main(String[] args) {
        Solution_35 solution = new Solution_35();
        int[] nums = new int[]{1, 3, 5, 6};
        int target = 5;
        int index = solution.searchInsert(nums, target);
        System.out.println("返回结果：" + index);
    }
}

class Solution_35 {
    public int searchInsert(int[] nums, int target) {
        int i = 0;
        for (; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return i;
    }
}