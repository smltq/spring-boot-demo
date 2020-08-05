package com.easy.leetcode;

import java.util.HashSet;
import java.util.Set;

/*
03. 数组中重复的数字
找出数组中重复的数字。


在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。

示例 1：

输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3


限制：

2 <= n <= 100000
*/
public class Sub3 {
    public static void main(String[] args) {
        Solution_3_2 solution = new Solution_3_2();
        int[] nums = new int[]{2, 3, 1, 0, 2, 5, 3};
        System.out.println("返回结果为：" + solution.findRepeatNumber(nums));
    }
}

class Solution_3_1 {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> sets = new HashSet<>();
        for (int i : nums) {
            if (sets.contains(i)) {
                return i;
            }
            sets.add(i);
        }
        return -1;
    }
}

class Solution_3_2 {
    public int findRepeatNumber(int[] nums) {
        int[] bit = new int[nums.length];
        for (int i : nums) {
            bit[i]++;
            if (bit[i] > 1) {
                return i;
            }
        }
        return -1;
    }
}