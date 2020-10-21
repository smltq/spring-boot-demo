package com.easy.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
56. 合并区间
给出一个区间的集合，请合并所有重叠的区间。

示例 1:

输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
输出: [[1,6],[8,10],[15,18]]
解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
示例 2:

输入: intervals = [[1,4],[4,5]]
输出: [[1,5]]
解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。

提示：

intervals[i][0] <= intervals[i][1]
 */
public class Sub56 {
    public static void main(String[] args) {
        Solution_56 solution = new Solution_56();
        int[][] nums = {{1, 4}, {2, 3}};
        int[][] result = solution.merge(nums);
        System.out.println("返回结果为：");
        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
    }
}

class Solution_56 {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        //先按第0列排序
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);
        //开始合并
        for (int i = 1; i < intervals.length; i++) {
            int l = intervals[i][0], r = intervals[i][1];
            int index = result.size() - 1;
            int[] last = result.get(index);
            //如果上一个右界大于等于当前左界，则需要合并
            if (last[1] >= l) {
                result.set(index, new int[]{last[0], Math.max(r, last[1])});
            } else {
                result.add(new int[]{l, r});
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}