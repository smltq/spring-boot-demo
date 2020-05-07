package com.easy.leetcode;

/*
48. 旋转图像

给定一个 n × n 的二维矩阵表示一个图像。

将图像顺时针旋转 90 度。

说明：

你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。

示例 1:

给定 matrix =
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

原地旋转输入矩阵，使其变为:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
示例 2:

给定 matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
],

原地旋转输入矩阵，使其变为:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]
 */

import java.util.Arrays;

/**
 * 输入:
 * [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * 输出
 * [[15,13,14,5],[7,3,4,1],[12,6,8,2],[16,9,10,11]]
 * 预期结果
 * [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 */
public class Sub48 {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};

        Solution_48 solution = new Solution_48();
        solution.rotate(matrix);
        System.out.println(matrix[0][1]);
        System.out.println("输出：");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}

/**
 * 四个角旋转
 */
class Solution_48 {
    /**
     * 1==>matrix[row][col]
     * 2==>matrix[n-col-1][row]
     * 3==>matrix[n-row-1][n-col-1]
     * 4==>matrix[col][n-row-1]
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n / 2; row++) {
            for (int col = row; col < n - row - 1; col++) {
                int t = matrix[row][col];
                matrix[row][col] = matrix[n - col - 1][row];//1
                matrix[n - col - 1][row] = matrix[n - row - 1][n - col - 1];//2
                matrix[n - row - 1][n - col - 1] = matrix[col][n - row - 1];//3
                matrix[col][n - row - 1] = t;//4
            }
        }
    }
}