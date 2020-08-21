package com.easy.leetcode;

import java.util.ArrayList;
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
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println("返回结果为：" + solution.threeSum(nums));
    }
}

class Solution_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        int[] num = nums;// Arrays.stream(nums).distinct().toArray();
        List<List<Integer>> ansList = new ArrayList<>();
        int len = num.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    if (num[i] + num[j] + num[k] == 0) {
                        List<Integer> item = new ArrayList<>();
                        item.add(num[i]);
                        item.add(num[j]);
                        item.add(num[k]);
                        Collections.sort(item);
                        if (!ansList.contains(item)) {
                            ansList.add(item);
                        }
                    }
                }
            }
        }
        return ansList;
    }
}