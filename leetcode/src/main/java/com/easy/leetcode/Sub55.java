package com.easy.leetcode;

/*
55. 跳跃游戏
给定一个非负整数数组，你最初位于数组的第一个位置。

数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个位置。

示例 1:

输入: [2,3,1,1,4]
输出: true
解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
示例 2:

输入: [3,2,1,0,4]
输出: false
解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 */
public class Sub55 {
    public static void main(String[] args) {
        Solution_55 solution = new Solution_55();
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println("返回结果为：" + solution.canJump(nums));
    }
}

class Solution_55 {
    public boolean canJump(int[] nums) {
        return false;
    }
}