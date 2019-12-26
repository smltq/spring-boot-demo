package com.easy.leetcode;

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