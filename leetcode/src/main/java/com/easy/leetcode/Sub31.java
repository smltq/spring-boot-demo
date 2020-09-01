package com.easy.leetcode;

import java.util.Arrays;

/*
31. 下一个排列
实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。

如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。

必须原地修改，只允许使用额外常数空间。

以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1
 */
public class Sub31 {
    public static void main(String[] args) {
        int[] nums = {1, 3, 2};//==>2,1,3
        Solution_31 solution = new Solution_31();
        solution.nextPermutation(nums);
        System.out.println("返回结果为：");
        Arrays.stream(nums).mapToObj(a -> a + ",").forEach(System.out::print);
    }
}

class Solution_31 {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 1;
        //从后往前扫描
        while (i > 0 && nums[i] < nums[i - 1]) {
            i--;
        }

        if (i > 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        reverse(nums, i + 1);
    }

    //倒序
    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    //交换两个数
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}