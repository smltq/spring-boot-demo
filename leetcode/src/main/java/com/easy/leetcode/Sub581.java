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
        Solution_581_2 solution = new Solution_581_2();
        System.out.println("输出：" + solution.findUnsortedSubarray(nums));
    }
}

/**
 * 排序法
 */
class Solution_581_1 {
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

/**
 * 1.先统计乱序的起始和终止位置，并求出这段序列里的最大最小值
 * 2.然后乱序列前面的递增序列从后往前与min比较
 * 3.乱序列后面的递增序列从前往后与max比较
 */
class Solution_581_2 {
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        int len = nums.length;
        //乱序列里的最小值
        int min = Integer.MAX_VALUE;
        //乱序列里的最大值
        int max = Integer.MIN_VALUE;
        //乱序列的起始位置
        int start = 0;
        //乱序列的终止位置
        int end = 0;

        //找乱序的起始索引
        for (int i = 0; i < len - 1; i++) {
            if (nums[i + 1] < nums[i]) {
                start = i;
                break;
            }
        }

        //找乱序的终止索引
        for (int i = len - 1; i > 0; i--) {
            if (nums[i - 1] > nums[i]) {
                end = i;
                break;
            }
        }

        //找乱序中的最大值、最小值
        for (int i = start; i <= end; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        if (start == end) return 0;

        //找开始索引,乱序列前面的递增序列从后往前与min比较
        while (start > 0) {
            if (nums[start - 1] > min) {
                start--;
            } else {
                break;
            }
        }

        //找结束索引,乱序列后面的递增序列从前往后与max比较
        while (end < len - 1) {
            if (nums[end + 1] < max) {
                end++;
            } else {
                break;
            }
        }

        return end - start + 1;
    }
}