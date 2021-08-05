package com.easy.leetcode.introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    977. 有序数组的平方
    给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。

    示例 1：

    输入：nums = [-4,-1,0,3,10]
    输出：[0,1,9,16,100]
    解释：平方后，数组变为 [16,1,0,9,100]
    排序后，数组变为 [0,1,9,16,100]
    示例 2：

    输入：nums = [-7,-3,2,3,11]
    输出：[4,9,9,49,121]


    提示：

    1 <= nums.length <= 10^4
    -10^4 <= nums[i] <= 10^4
    nums 已按 非递减顺序 排序
 */
public class Sub977 {
    public static void main(String[] args) {
        Solution_977_2 solution = new Solution_977_2();
        int[] nums = {-7, -3, 2, 3, 11};
        int[] ans = solution.sortedSquares(nums);
        System.out.println("结果：" + Arrays.toString(ans));
    }
}

/**
 * 思路一
 * 1.分解成两个递增列表
 * 2.对两个递增列表进行合并排序
 */
class Solution_977_1 {
    public int[] sortedSquares(int[] nums) {
        List<Integer> ans1 = new ArrayList<>();
        List<Integer> ans2 = new ArrayList<>();
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int t = nums[i];
            if (t < 0) {
                ans1.add(0, t * t);
            } else {
                ans2.add(t * t);
            }
        }

        //对两个递增list进行归并排序
        int index = 0;
        while (!ans1.isEmpty() && !ans2.isEmpty()) {
            int t1 = ans1.get(0);
            int t2 = ans2.get(0);
            if (t1 > t2) {
                ans[index++] = t2;
                ans2.remove(0);
            } else {
                ans[index++] = t1;
                ans1.remove(0);
            }
        }
        if (ans1.isEmpty()) {
            for (int t : ans2) {
                ans[index++] = t;
            }
        }
        if (ans2.isEmpty()) {
            for (int t : ans1) {
                ans[index++] = t;
            }
        }
        return ans;
    }
}

/**
 * 思路二
 * 1.找到最大负数索引negative，该索引距离0最近
 * 2.利用双指针，从negative处开始往左右两边递增，平方后结果按大小顺序放入ans数组中并返回
 */
class Solution_977_2 {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int negative = -1;
        for (int i = 0; i < n; ++i) {
            if (nums[i] < 0) {
                negative = i;
            } else {
                break;
            }
        }

        int[] ans = new int[n];
        int index = 0, i = negative, j = negative + 1;
        while (i >= 0 || j < n) {
            if (i < 0) {
                ans[index] = nums[j] * nums[j];
                ++j;
            } else if (j == n) {
                ans[index] = nums[i] * nums[i];
                --i;
            } else if (nums[i] * nums[i] < nums[j] * nums[j]) {
                ans[index] = nums[i] * nums[i];
                --i;
            } else {
                ans[index] = nums[j] * nums[j];
                ++j;
            }
            ++index;
        }

        return ans;
    }
}
