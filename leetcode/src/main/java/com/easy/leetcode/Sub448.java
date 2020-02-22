package com.easy.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
448. 找到所有数组中消失的数字

给定一个范围在  1 ≤ a[i] ≤ n ( n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。

找到所有在 [1, n] 范围之间没有出现在数组中的数字。

您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。

示例:

输入:
[4,3,2,7,8,2,3,1]

输出:
[5,6]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array
 */
public class Sub448 {
    public static void main(String[] args) {
        Solution_448 solution = new Solution_448();
        int[] nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> results = solution.findDisappearedNumbers(nums);
        System.out.println("返回结果：" + results.toString());
    }
}

class Solution_448 {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        return result;
    }
}