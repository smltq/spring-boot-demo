package com.easy.leetcode;

/*

437. 路径总和 III

给定一个二叉树，它的每个结点都存放着一个整数值。

找出路径和等于给定数值的路径总数。

路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。

示例：

root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

返回 3。和等于 8 的路径有:

1.  5 -> 3
2.  5 -> 2 -> 1
3.  -3 -> 11

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/path-sum-iii
 */
public class Sub437 {
    public static void main(String[] args) {
        Solution_437 solution = new Solution_437();
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);
        root.right.right = new TreeNode(11);

        int count = solution.pathSum(root, 8);
        System.out.println("返回结果为：" + count);
    }
}

class Solution_437 {
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        int result = countPath(root, sum);
        int a = pathSum(root.left, sum);
        int b = pathSum(root.right, sum);
        return result + a + b;
    }

    public int countPath(TreeNode root, int sum) {
        if (root == null) return 0;
        sum = sum - root.val;
        int result = (sum == 0 ? 1 : 0);
        return result + countPath(root.left, sum) + countPath(root.right, sum);
    }
}