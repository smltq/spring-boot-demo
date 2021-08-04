package com.easy.leetcode.introduction;

/*
    278. 第一个错误的版本

    你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。

    假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。

    你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。


    示例 1：

    输入：n = 5, bad = 4
    输出：4
    解释：
    调用 isBadVersion(3) -> false
    调用 isBadVersion(5) -> true
    调用 isBadVersion(4) -> true
    所以，4 是第一个错误的版本。
    示例 2：

    输入：n = 1, bad = 1
    输出：1
 */

public class Sub278 {
    public static void main(String[] args) {
        Solution_278 solution = new Solution_278();
        int ans = solution.firstBadVersion(5);
        System.out.println("结果：" + ans);
    }
}

class Solution_278 extends VersionControl {
    public int firstBadVersion(int n) {
        int start = 0, end = n;
        while (start < end) {
            int mid = start + (end - start) / 2; //==>防止溢出，等价于(start+end)/2
            if (isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}

class VersionControl {
    int bad = 4;

    public boolean isBadVersion(int version) {
        return version == bad;
    }
}