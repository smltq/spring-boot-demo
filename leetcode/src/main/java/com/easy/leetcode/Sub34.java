package com.easy.leetcode;

import java.util.Arrays;

/*
34. 在排序数组中查找元素的第一个和最后一个位置
给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。

你的算法时间复杂度必须是 O(log n) 级别。

如果数组中不存在目标值，返回 [-1, -1]。

示例 1:

输入: nums = [5,7,7,8,8,10], target = 8
输出: [3,4]
示例 2:

输入: nums = [5,7,7,8,8,10], target = 6
输出: [-1,-1]
 */
public class Sub34 {
    public static void main(String[] args) {
        Solution_34 solution = new Solution_34();
        int[] nums = {5, 7, 7, 8, 8, 10};
        System.out.println("返回结果为：" + Arrays.toString(solution.searchRange(nums, 5)));
    }
}

class Solution_34 {
    public int[] searchRange(int[] nums, int target) {
        int[] ans = {-1, -1};
        int len = nums.length - 1;
        int index = binarySearch(nums, target);
        if (index != -1) {
            int r = index, l = index;
            while (l >= 0 && nums[l] == target) l--;
            while (r <= len && nums[r] == target) r++;
            ans[0] = l + 1;
            ans[1] = r - 1;
        }
        return ans;
    }

    public int binarySearch(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }
}