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

public class Sub48 {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        Solution_48 solution = new Solution_48();
        solution.rotate(matrix);

        System.out.println("输出：");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}

class Solution_48 {
    public void rotate(int[][] matrix) {
        int iLen = matrix.length;
        int[][] m = new int[iLen][iLen];
        for (int i = 0; i < iLen; i++) {
            int jLen = matrix[i].length;
            for (int j = 0; j < jLen; j++) {
                int t = matrix[i][j];
                matrix[i][j] = matrix[iLen - j - 1][i];
                matrix[iLen - j - 1][i] = t;
            }
        }
    }
}