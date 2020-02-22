package com.easy.leetcode;

import java.util.*;

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
        Solution_448_2 solution = new Solution_448_2();
        int[] nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> results = solution.findDisappearedNumbers(nums);
        System.out.println("返回结果：" + results.toString());
    }
}

/*
 方法一：使用哈希表

假设数组大小为 N，它应该包含从 1 到 N 的数字。但是有些数字丢失了，我们要做的是记录我们在数组中遇到的数字。然后从 1到N 检查哈希表中没有出现的数字。

算法：

1.用一个哈希表 hash 来记录我们在数组中遇到的数字。我们也可以用集合 set 来记录，因为我们并不关心数字出现的次数。
2.然后遍历给定数组的元素，插入到哈希表中，即使哈希表中已经存在某元素，再次插入了也会覆盖
3.现在我们知道了数组中存在那些数字，只需从 1到N 范围中找到缺失的数字。
4.从 1到N 检查哈希表中是否存在，若不存在则添加到存放结果列表中。
 */
class Solution_448_1 {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        Set sets = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            sets.add(nums[i]);
        }

        for (int i = 1; i <= nums.length; i++) {
            if (!sets.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }
}

/*
方法二：原地修改

由于数组的元素取值范围是 [1, N]，我们可以在输入数组本身以某种方式标记已访问过的数字，然后再找到缺失的数字。

算法：

1.遍历输入数组的每个元素一次。
2.把 |nums[i]|-1 索引位置的元素标记为负数。即 nums[|nums[i]|- 1]=nums[∣nums[i]∣−1]*−1。
3.遍历数组，若当前数组元素 nums[i] 为负数，说明我们在数组中存在数字 i+1。
*/
class Solution_448_2 {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0) {
                nums[index] *= -1;
            }
        }

        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }
        return result;
    }
}