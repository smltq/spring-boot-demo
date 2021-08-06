package com.easy.leetcode.introduction;

import java.util.Arrays;

/*
    283. 移动零
    给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

    示例:

    输入: [0,1,0,3,12]
    输出: [1,3,12,0,0]
    说明:

    必须在原数组上操作，不能拷贝额外的数组。
    尽量减少操作次数。
 */
public class Sub283 {
    public static void main(String[] args) {
        Solution_283 solution = new Solution_283();
        int[] nums = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums);
        System.out.println("结果：" + Arrays.toString(nums));
    }
}

class Solution_283 {
    public void moveZeroes(int[] nums) {
        int fastIndex = 0, slowIndex = 0, n = nums.length;
        for (; fastIndex < n; ) {
            if (nums[fastIndex] != 0) {
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
            fastIndex++;
        }
        for (int i = slowIndex; i < n; i++) {
            nums[i] = 0;
        }
    }
}
