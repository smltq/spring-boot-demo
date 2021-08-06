package com.easy.leetcode.introduction;

import java.util.Arrays;

/*
    189. 旋转数组
    给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。

    进阶：

    尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
    你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？


    示例 1:

    输入: nums = [1,2,3,4,5,6,7], k = 3
    输出: [5,6,7,1,2,3,4]
    解释:
    向右旋转 1 步: [7,1,2,3,4,5,6]
    向右旋转 2 步: [6,7,1,2,3,4,5]
    向右旋转 3 步: [5,6,7,1,2,3,4]
    示例 2:

    输入：nums = [-1,-100,3,99], k = 2
    输出：[3,99,-1,-100]
    解释:
    向右旋转 1 步: [99,-1,-100,3]
    向右旋转 2 步: [3,99,-1,-100]


    提示：

    1 <= nums.length <= 2 * 10^4
    -2^31 <= nums[i] <= 2^31 - 1
    0 <= k <= 10^5
 */
public class Sub189 {
    public static void main(String[] args) {
        Solution_189 solution = new Solution_189();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        solution.rotate(nums, 3);
        System.out.println("结果：" + Arrays.toString(nums));
    }
}

class Solution_189 {
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        while (k > 0) {
            int n = len - 1, t = nums[n];
            while (n > 0) {
                nums[n] = nums[n - 1];
                n--;
            }
            nums[0] = t;
            k--;
        }
    }
}
