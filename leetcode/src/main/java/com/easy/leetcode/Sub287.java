package com.easy.leetcode;

import java.util.Arrays;

/*

287. 寻找重复数

给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。

示例 1:

输入: [1,3,4,2,2]
输出: 2
示例 2:

输入: [3,1,3,4,2]
输出: 3
说明：

不能更改原数组（假设数组是只读的）。
只能使用额外的 O(1) 的空间。
时间复杂度小于 O(n2) 。
数组中只有一个重复的数字，但它可能不止重复出现一次。
 */
public class Sub287 {
    public static void main(String[] args) {
        int[] nums = {3, 1, 3, 4, 2};
        Solution_287 solution = new Solution_287();
        System.out.println("重复数为：" + solution.findDuplicate(nums));
    }
}

/**
 * 二分查找
 */
class Solution_287 {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        return binarySearch(nums, 0, nums.length);
    }

    public int binarySearch(int[] nums, int s, int e) {
        if (s > e) {
            return 0;
        }
        int mid = (s + e) / 2;
        if (nums[mid] > mid) {
            return binarySearch(nums, mid, e);
        } else {
            if (nums[mid - 1] == nums[mid] || nums[mid] == nums[mid + 1]) {
                return nums[mid];
            } else {
                return binarySearch(nums, s, mid);
            }
        }
    }
}