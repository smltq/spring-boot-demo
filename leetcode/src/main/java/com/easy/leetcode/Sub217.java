package com.easy.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
217. 存在重复元素
给定一个整数数组，判断是否存在重复元素。

如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。



示例 1:

输入: [1,2,3,1]
输出: true
示例 2:

输入: [1,2,3,4]
输出: false
示例 3:

输入: [1,1,1,3,3,4,3,2,4,2]
输出: true
*/
public class Sub217 {
    public static void main(String[] args) {
        Solution_217_1 solution = new Solution_217_1();
        int[] nums = {1, 2, 3, 1};
        System.out.println("结果：" + solution.containsDuplicate(nums));
    }
}

/**
 * Stream解法
 */
class Solution_217_1 {
    public boolean containsDuplicate(int[] nums) {
        return !(Arrays.stream(nums).distinct().count() == nums.length);
    }
}

/**
 * 利用HashSet
 */
class Solution_217_2 {
    public boolean containsDuplicate(int[] nums) {
        Set sets = new HashSet<Integer>();
        for (int v : nums) {
            if (sets.contains(v)) {
                return true;
            }
            sets.add(v);
        }
        return false;
    }
}