package com.easy.leetcode.introduction;

/*
  704. 二分查找
  给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。


  示例 1:

  输入: nums = [-1,0,3,5,9,12], target = 9
  输出: 4
  解释: 9 出现在 nums 中并且下标为 4
  示例 2:

  输入: nums = [-1,0,3,5,9,12], target = 2
  输出: -1
  解释: 2 不存在 nums 中因此返回 -1
 */
public class Sub704 {
    public static void main(String[] args) {
        Solution_704 solution = new Solution_704();
        int[] nums = {-1,0,3,5,9,12};
        int target = 9;
        int ans = solution.search(nums, target);
        System.out.println("结果：" + ans);
    }
}

class Solution_704 {
    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length, target);
    }

    public int binarySearch(int[] nums, int start, int end, int target) {
        int mid = (start + end) / 2;
        if (start >= end) {
            return -1;
        }
        if (nums[mid] > target) {
            return binarySearch(nums, start, mid, target);
        } else if (nums[mid] < target) {
            return binarySearch(nums, mid + 1, end, target);
        } else {
            return mid;
        }
    }
}
