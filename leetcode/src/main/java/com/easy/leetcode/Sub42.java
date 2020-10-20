package com.easy.leetcode;

/*
42. 接雨水
给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。

示例:

输入: [0,1,0,2,1,0,1,3,2,1,2,1]
输出: 6
 */
public class Sub42 {
    public static void main(String[] args) {
        Solution_42 solution = new Solution_42();
        int[] nums = {};
        System.out.println("返回结果为：" + solution.trap(nums));
    }
}

/**
 * 动态编程
 * 1.左往右扫一遍，存最大值。
 * 2.右往左扫一遍，存最大值。
 * 3.计算结果，取左右最大值的最小值与当前值相减，就是每个节点能存的雨水值。
 */
class Solution_42 {
    public int trap(int[] height) {
        int len = height.length, ans = 0;
        int[] max_l = new int[len];
        int[] max_r = new int[len];
        if (len == 0 || len == 1 || len == 2) {
            return ans;
        }
        max_l[0] = height[0];
        max_r[len - 1] = height[len - 1];
        for (int i = 1; i < len; i++) {
            max_l[i] = Math.max(height[i], max_l[i - 1]);
        }
        for (int i = len - 2; i >= 0; i--) {
            max_r[i] = Math.max(height[i], max_r[i + 1]);
        }
        for (int i = 1; i < len - 1; i++) {
            ans += Math.min(max_l[i], max_r[i]) - height[i];
        }
        return ans;
    }
}