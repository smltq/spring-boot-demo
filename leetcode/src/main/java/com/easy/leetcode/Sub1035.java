package com.easy.leetcode;

/**
 * 1035. 不相交的线
 * 我们在两条独立的水平线上按给定的顺序写下 A 和 B 中的整数。
 * <p>
 * 现在，我们可以绘制一些连接两个数字 A[i] 和 B[j] 的直线，只要 A[i] == B[j]，且我们绘制的直线不与任何其他连线（非水平线）相交。
 * <p>
 * 以这种方法绘制线条，并返回我们可以绘制的最大连线数。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：A = [1,4,2], B = [1,2,4]
 * 输出：2
 * 解释：
 * 我们可以画出两条不交叉的线，如上图所示。
 * 我们无法画出第三条不相交的直线，因为从 A[1]=4 到 B[2]=4 的直线将与从 A[2]=2 到 B[1]=2 的直线相交。
 */
public class Sub1035 {
    public static void main(String[] args) {
        int[] A = {1, 3, 5};
        int[] B = {1, 5, 3};
        Solution_1035_1 solution = new Solution_1035_1();
        int result = solution.maxUncrossedLines(A, B);
        System.out.println("返回结果为：" + result);
    }
}

/**
 * dp 解法
 */
class Solution_1035_1 {
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[A.length][B.length];
    }
}
