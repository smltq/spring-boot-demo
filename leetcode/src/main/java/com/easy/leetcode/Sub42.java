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
        int[] nums = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("返回结果为：" + solution.trap(nums));
    }
}

class Solution_42 {
    public int trap(int[] height) {
        int ans = 0, len = height.length, l = 0, r = 0;
        //定位l
        while (height[l] == 0 || height[l] <= height[l + 1]) l++;
        r = l + 1;

        while (r < len - 1) {
            while (height[r] < height[l]) r++;

            if (height[l] > 0 && height[l] <= height[r]) {
                int tValue = Math.min(height[l], height[r]);

            } else {
                l = r;
            }
        }
        return 0;
    }
}