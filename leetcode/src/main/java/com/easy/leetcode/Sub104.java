package com.easy.leetcode;

/*

104. 二叉树的最大深度

给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

示例：
给定二叉树 [3,9,20,null,null,15,7]，

    3
   / \
  9  20
    /  \
   15   7
返回它的最大深度 3 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 */
public class Sub104 {
    public static void main(String[] args) {
        Solution_104 solution = new Solution_104();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        int maxDepth = solution.maxDepth(root);
        System.out.println("最大深度为：" + maxDepth);
    }
}

/**
 * 递归
 */
class Solution_104 {
    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }

    public int maxDepth(TreeNode root, int level) {
        if (root == null) return level;
        int left = maxDepth(root.left, level + 1);
        int right = maxDepth(root.right, level + 1);
        return Math.max(left, right);
    }
}