package com.easy.leetcode;

import java.util.HashSet;
import java.util.Set;

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
        Solution_136_1 solution = new Solution_136_1();
        int[] nums = {2, 2, 1};
        System.out.println("只出现一次的元素为：" + solution.singleNumber(nums));
    }
}

/**
 * 数学公式解法：2*(a+b+c)-(a+a+b+b+c)=c==>a+a+b+b+c-2*a-2*b-2*c=-c
 */
class Solution_136_1 {
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet();
        Integer sum = 0;
        for (int num : nums) {
            set.add(num);
            sum = sum + num;
        }
        for (int item : set) {
            sum = sum - 2 * item;
        }
        return -sum;
    }
}

/**
 * 位操作
 * 概念
 * 1.如果我们对 0 和二进制位做 XOR 运算，得到的仍然是这个二进制位
 * a^0 == a
 * 2.如果我们对相同的二进制位做 XOR 运算，返回的结果是 0
 * a^a ==0
 * 3.XOR 满足交换律和结合律
 * a^b^a =(a^a)^b=0^b =b
 * 所以我们只需要将所有的数进行 XOR 操作，得到那个唯一的数字。
 */
class Solution_136_2 {
    public int singleNumber(int[] nums) {
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            x = x ^ nums[i];
        }
        return x;
    }
}
