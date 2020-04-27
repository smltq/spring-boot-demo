package com.easy.leetcode;

/*
53. 最大子序和
给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

示例:

输入: [-2,1,-3,4,-1,2,1,-5,4],
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。

进阶:

如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 */
public class Sub53 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2};
        Solution_53_2 solution = new Solution_53_2();
        System.out.println("最大和为：" + solution.maxSubArray(nums));
    }
}

/**
 * 动态规划
 */
class Solution_53_1 {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], 0) + nums[i];
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}

/**
 * 动态规划(改进空间复杂度）
 */
class Solution_53_2 {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int ans = nums[0], current = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(current + nums[i], nums[i]);
            ans = Math.max(ans, current);
        }
        return ans;
    }
}