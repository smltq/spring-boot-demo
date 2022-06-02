package com.easy.leetcode;

/*

169. 多数元素

给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。

你可以假设数组是非空的，并且给定的数组总是存在多数元素。

示例 1:

输入: [3,2,3]
输出: 3
示例 2:

输入: [2,2,1,1,1,2,2]
输出: 2

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/majority-element
 */
public class Sub169 {
    public static void main(String[] args) {
        Solution_169_2 solution = new Solution_169_2();
        int[] nums = new int[]{2, 2, 1, 1, 1, 2, 2};
        System.out.println("众数为：" + solution.majorityElement(nums));
    }
}

/**
 * Boyer- Moore算法
 */
class Solution_169_1 {
    public int majorityElement(int[] nums) {
        int count = 0, candidate = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate ? 1 : -1);
        }
        return candidate;
    }
}

/**
 * 博耶-摩尔多数投票算法（英语：Boyer–Moore majority vote algorithm）
 */
class Solution_169_2 {
    public int majorityElement(int[] nums) {
        int m = 0, i = 0;
        for (int num : nums) {
            if (i == 0) {
                m = num;
                i = 1;
            } else if (m == num) {
                i++;
            } else i--;
        }
        return m;
    }
}

