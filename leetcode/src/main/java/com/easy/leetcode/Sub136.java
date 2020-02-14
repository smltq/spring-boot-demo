package com.easy.leetcode;

import java.util.HashMap;

/*
给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

说明：

你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

示例 1:

输入: [2,2,1]
输出: 1
示例 2:

输入: [4,1,2,1,2]
输出: 4

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/single-number
 */
public class Sub136 {
    public static void main(String[] args) {
        Solution_136 solution = new Solution_136();
        int[] nums = {2, 2, 1};
        System.out.println("只出现一次的元素为：" + solution.singleNumber(nums));
    }
}

class Solution_136 {
    public int singleNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {

            }
        }
        return -1;
    }
}
