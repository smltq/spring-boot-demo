package com.easy.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*

给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。

示例:

给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum
 */
public class Sub1 {
    public static void main(String[] args) {
        Solution_1 solution = new Solution_1();
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] result = solution.twoSum(nums, target);
        System.out.println("返回结果为：" + Arrays.toString(result));
    }
}

class Solution_1 {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int v = target - nums[i];
            if (map.containsKey(v) && i != map.get(v)) {
                result[0] = i;
                result[1] = map.get(v);
                return result;
            }
        }
        return result;
    }
}
