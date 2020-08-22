package com.easy.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
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
        int[] nums = {0, -4, -5, 3, 1, 3, 4, 2, -5, 2, 4, 2, -5};
        System.out.println("返回结果为：" + solution.threeSum(nums));
    }
}

class Solution_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ansList = new ArrayList<>();
        //排序
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            if (nums[i] > 0) {
                break;
            }
            //双指针
            int target = -nums[i], start = i + 1, end = len - 1;
            while (start < end) {
                int current = nums[start] + nums[end];
                if (start > i + 1 && nums[start] == nums[start - 1]) {
                    start++;
                    continue;
                }
                if (current == target) {
                    List<Integer> item = new ArrayList<>();
                    item.add(nums[i]);
                    item.add(nums[start]);
                    item.add(nums[end]);
                    ansList.add(item);
                    start++;
                } else if (current > target) {
                    end--;
                } else {
                    start++;
                }
            }
        }
        return ansList;
    }
}