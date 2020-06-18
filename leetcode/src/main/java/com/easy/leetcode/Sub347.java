package com.easy.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
347. 前 K 个高频元素
给定一个非空的整数数组，返回其中出现频率前 k 高的元素。



示例 1:

输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]
示例 2:

输入: nums = [1], k = 1
输出: [1]


提示：

你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
你可以按任意顺序返回答案。
 */
public class Sub347 {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        Solution_347_1 solution = new Solution_347_1();
        System.out.println("返回结果为：" + Arrays.toString(solution.topKFrequent(nums, k)));
    }
}

class Solution_347_1 {
    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> mapCount = new HashMap<>();
        //统计出现频率
        for (int n : nums) {
            mapCount.put(n, mapCount.getOrDefault(n, 0) + 1);
        }

        //创建大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> mapCount.get(a) - mapCount.get(b));
        for (int key : mapCount.keySet()) {
            maxHeap.add(key);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[] ans = new int[k];
        int t = k - 1;
        while (!maxHeap.isEmpty()) {
            ans[t--] = maxHeap.poll();
        }
        return ans;
    }
}