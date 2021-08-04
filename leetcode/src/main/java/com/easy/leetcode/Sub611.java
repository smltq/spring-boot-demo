package com.easy.leetcode;

import java.util.Arrays;

/*
611. 有效三角形的个数
给定一个包含非负整数的数组，你的任务是统计其中可以组成三角形三条边的三元组个数。

示例 1:

输入: [2,2,3,4]
输出: 3
解释:
有效的组合是:
2,3,4 (使用第一个 2)
2,3,4 (使用第二个 2)
2,2,3
注意:

数组长度不超过1000。
数组里整数的范围为 [0, 1000]。
 */
public class Sub611 {
    public static void main(String[] args) {
        Solution_611 solution = new Solution_611();
        int[] nums = {2, 2, 3, 4};
        int ans = solution.triangleNumber(nums);
        System.out.println("返回结果：" + ans);
    }
}

/**
 * 思路
 * 1.先对数组升序排序
 * 2.因为a<b<c，所以a+c>b,b+c>a必成立，只需要判断a+b>c是否成立
 */
class Solution_611 {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length, ans = 0;
        for (int i = 0; i < len - 2; i++) {
            for (int j = i + 1; j < len - 1; j++) {
                for (int k = j + 1; k < len; k++) {
                    int a = nums[i], b = nums[j], c = nums[k];
                    if (a + b > c) {
                        ans++;
                    } else {
                        break;
                    }
                }
            }
        }
        return ans;
    }
}
