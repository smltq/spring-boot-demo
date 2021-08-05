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
        Solution_977 solution = new Solution_977();
        int[] nums = {-7,-3,2,3,11};
        int[] ans = solution.sortedSquares(nums);
        System.out.println("结果：" + Arrays.toString(ans));
    }
}

class Solution_977 {
    public int[] sortedSquares(int[] nums) {
        List<Integer> ans1 = new ArrayList<>();
        List<Integer> ans2 = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int t = nums[i];
            if (t < 0) {
                ans1.add(0, t * t);
            } else {
                ans2.add(t * t);
            }
        }

        //对两个递增list进行归并排序
        while (!ans1.isEmpty()) {
            int t = ans1.remove(0);
            int index = 0;
            boolean add = false;
            while (index < ans2.size()) {
                if (ans2.get(index) > t) {
                    ans2.add(index, t);
                    add = true;
                    break;
                }
                index++;
            }
            if (!add) {
                ans2.add(t);
            }
        }
        return ans2.stream().mapToInt(i -> i).toArray();
    }
}
