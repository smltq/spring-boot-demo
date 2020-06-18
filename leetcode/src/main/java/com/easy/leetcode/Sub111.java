package com.easy.leetcode;

/*
111. 二叉树的最小深度

给定一个二叉树，找出其最小深度。

最小深度是从根节点到最近叶子节点的最短路径上的节点数量。

说明: 叶子节点是指没有子节点的节点。

示例:

给定二叉树 [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
返回它的最小深度  2.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree
 */
public class Sub111 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        Solution111 solution = new Solution111();
        int minDepth = solution.minDepth(root);
        System.out.println("最小深度为：" + minDepth);
    }
}

/**
 * 深搜+递归
 */
class Solution111 {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        return DFS(root);
    }

    int DFS(TreeNode node) {
        int level = Integer.MAX_VALUE;
        if (node.left == null && node.right == null) {
            return 1;
        }
        if (node.left != null) {
            level = Math.min(DFS(node.left), level);
        }
        if (node.right != null) {
            level = Math.min(DFS(node.right), level);
        }
        return level + 1;
    }
}