package com.easy.leetcode;

/*
461. 汉明距离

两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。

给出两个整数 x 和 y，计算它们之间的汉明距离。

注意：
0 ≤ x, y < 231.

示例:

输入: x = 1, y = 4

输出: 2

解释:
1   (0 0 0 1)
4   (0 1 0 0)
       ↑   ↑

上面的箭头指出了对应二进制位不同的位置。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/hamming-distance
 */
public class Sub461 {
    public static void main(String[] args) {
        Solution_461_2 solution = new Solution_461_2();
        System.out.println("输出结果为：" + solution.hammingDistance(1, 4));
    }
}

/**
 * 内置位计数功能
 */
class Solution_461_1 {
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}

/**
 * 移位
 */
class Solution_461_2 {
    public int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int bitCount = 0;
        while (xor != 0) {
            if (xor % 2 == 1) {
                bitCount++;
            }
            xor = xor >> 1;
        }
        return bitCount;
    }
}

/**
 * 布赖恩·克尼根算法
 */
class Solution_461_3 {
    public int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int bitCount = 0;
        while (xor != 0) {
            bitCount++;
            xor = xor & (xor - 1);
        }
        return bitCount;
    }
}