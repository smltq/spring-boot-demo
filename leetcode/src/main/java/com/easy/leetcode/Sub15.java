package com.easy.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
15. 三数之和
给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。

示例：

给定数组 nums = [-1, 0, 1, 2, -1, -4]，

满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]
 */
public class Sub15 {
    public static void main(String[] args) {
        Solution_15 solution = new Solution_15();
        int[] nums = {-1, 0, 1, 0};
        System.out.println("返回结果为：" + solution.threeSum(nums));
    }
}

class Solution_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ansList = new ArrayList<>();
        if (nums.length < 3) {
            return ansList;
        }
        if (nums[0] == nums[nums.length - 1] && nums[0] == 0) {
            List<Integer> item = new ArrayList<>();
            item.add(0);
            item.add(0);
            item.add(0);
            ansList.add(item);
            return ansList;
        }
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (i > 1 && nums[i] == nums[i - 1]) continue;
            if (nums[i] > 0) {
                break;
            }
            for (int j = i + 1; j < len; j++) {
                if (nums[j] == nums[j - 1]) continue;
                if (nums[i] + nums[j] > 0) {
                    break;
                }
                for (int k = j + 1; k < len; k++) {
                    if (nums[k] == nums[k - 1]) continue;
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> item = new ArrayList<>();
                        item.add(nums[i]);
                        item.add(nums[j]);
                        item.add(nums[k]);
                        ansList.add(item);
                    }
                }
            }
        }
        return ansList;
    }
}