package com.easy.leetcode;

/*
33. 搜索旋转排序数组
给你一个升序排列的整数数组 nums ，和一个整数 target 。

假设按照升序排序的数组在预先未知的某个点上进行了旋转。（例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] ）。

请你在数组中搜索 target ，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。


示例 1：

输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4
示例 2：

输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1
示例 3：

输入：nums = [1], target = 0
输出：-1


提示：

1 <= nums.length <= 5000
-10^4 <= nums[i] <= 10^4
nums 中的每个值都 独一无二
nums 肯定会在某个点上旋转
-10^4 <= target <= 10^4
 */
public class Sub33 {
    public static void main(String[] args) {
        Solution_33 solution = new Solution_33();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("返回结果为：" + solution.search(nums, 0));
    }
}

class Solution_33 {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            //System.out.println(String.format("l=%d,r=%d,mid=%d", l, r, mid));
            if (nums[mid] == target) {
                return mid;
            }

            //先根据 nums[mid] 与 nums[l] 的关系判断 mid 是在左段还是右段
            if (nums[mid] > nums[l]) { //mid落在左段
                //再判断 target 是在 mid 的左边还是右边，从而调整左右边界 l 和 r
                if (target == nums[l]) {
                    return l;
                } else if (target > nums[l] && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {        //mid落在右段
                if (target == nums[r]) {
                    return r;
                } else if (target < nums[r] && target > nums[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}