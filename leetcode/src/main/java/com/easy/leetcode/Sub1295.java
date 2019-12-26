package com.easy.leetcode;
/*
给你一个整数数组 nums，请你返回其中位数为 偶数 的数字的个数。

 

示例 1：

输入：nums = [12,345,2,6,7896]
输出：2
解释：
12 是 2 位数字（位数为偶数） 
345 是 3 位数字（位数为奇数）  
2 是 1 位数字（位数为奇数） 
6 是 1 位数字 位数为奇数） 
7896 是 4 位数字（位数为偶数）  
因此只有 12 和 7896 是位数为偶数的数字
示例 2：

输入：nums = [555,901,482,1771]
输出：1
解释：
只有 1771 是位数为偶数的数字。
 

提示：

1 <= nums.length <= 500
1 <= nums[i] <= 10^5

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/find-numbers-with-even-number-of-digits
 */
public class Sub1295 {
    public static void main(String[] args) throws InterruptedException {
        Solution_1295 solution = new Solution_1295();
        int[] nums = new int[]{
                12, 345, 2, 6, 7896
        };
        int count = solution.findNumbers(nums);
        System.out.println("位数是偶数个数为：" + count);
    }
}

class Solution_1295 {
    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

    static int sizeOfInt(int x) {
        for (int i = 0; ; i++)
            if (x <= sizeTable[i]) {
                return i + 1;
            }
    }

    public int findNumbers(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int even = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((sizeOfInt(nums[i]) & 1) == 0) {
                even++;
            }
        }
        return even;
    }
}