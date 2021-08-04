package com.easy.leetcode.introduction;

/*
    35. 搜索插入位置
    给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。

    请必须使用时间复杂度为 O(log n) 的算法。

    示例 1:

    输入: nums = [1,3,5,6], target = 5
    输出: 2
    示例 2:

    输入: nums = [1,3,5,6], target = 2
    输出: 1
    示例 3:

    输入: nums = [1,3,5,6], target = 7
    输出: 4
    示例 4:

    输入: nums = [1,3,5,6], target = 0
    输出: 0
    示例 5:

    输入: nums = [1], target = 0
    输出: 0


    提示:

    1 <= nums.length <= 10^4
    -10^4 <= nums[i] <= 10^4
    nums 为无重复元素的升序排列数组
    -10^4 <= target <= 10^4
 */

public class Sub35 {
    public static void main(String[] args) {
        Solution_35 solution = new Solution_35();
        int[] nums = {1, 3, 5, 6};
        int target = 0;
        int ans = solution.searchInsert(nums, target);
        System.out.println("结果：" + ans);
    }
}

class Solution_35 {
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] == target) {
                return m;
            } else if (nums[m] > target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}