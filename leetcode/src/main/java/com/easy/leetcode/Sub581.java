package com.easy.leetcode;

import java.util.Arrays;

/*
581. 最短无序连续子数组
给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。

你找到的子数组应是最短的，请输出它的长度。

示例 1:

输入: [2, 6, 4, 8, 10, 9, 15]
输出: 5
解释: 你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
说明 :

输入的数组长度范围在 [1, 10,000]。
输入的数组可能包含重复元素 ，所以升序的意思是<=。
 */
public class Sub581 {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 6, 4, 8, 10, 9, 15};
        Solution_581 solution = new Solution_581();
        System.out.println("输出：" + solution.findUnsortedSubarray(nums));
    }
}

/**
 * 排序法
 */
class Solution_581 {
    public int findUnsortedSubarray(int[] nums) {
        int[] numsSort = nums.clone();
        Arrays.sort(numsSort);
        int s = numsSort.length, e = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != numsSort[i]) {
                s = Math.min(s, i);
                e = Math.max(e, i);
            }
        }
        return (e - s >= 0 ? e - s + 1 : 0);
    }
}