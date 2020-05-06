package com.easy.leetcode;

/*
338. 比特位计数
给定一个非负整数 num。对于 0 ≤ i ≤ num 范围中的每个数字 i ，计算其二进制数中的 1 的数目并将它们作为数组返回。

示例 1:

输入: 2
输出: [0,1,1]
示例 2:

输入: 5
输出: [0,1,1,2,1,2]
进阶:

给出时间复杂度为O(n*sizeof(integer))的解答非常容易。但你可以在线性时间O(n)内用一趟扫描做到吗？
要求算法的空间复杂度为O(n)。
你能进一步完善解法吗？要求在C++或任何其他语言中不使用任何内置函数（如 C++ 中的 __builtin_popcount）来执行此操作。
 */
public class Sub338 {
    public static void main(String[] args) {
        Solution_338_2 solution = new Solution_338_2();
        System.out.println("输出：" + solution.countBits(4));
    }
}

/**
 * 内置位统计函数实现
 */
class Solution_338_1 {
    public int[] countBits(int num) {
        int[] ans = new int[num + 1];
        for (int i = 0; i <= num; i++) {
            ans[i] = Integer.bitCount(i);
        }
        return ans;
    }
}

/**
 * 动态规划实现
 * 状态转移函数：P(x)=P(x/2)+(x mod 2)
 * 例如：
 * 1.P（2）=P（2/2）+(2%2)=1
 * 2.P（3）=P（3/2）+(3%2)=2
 */
class Solution_338_2 {
    public int[] countBits(int num) {
        int[] ans = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }
}