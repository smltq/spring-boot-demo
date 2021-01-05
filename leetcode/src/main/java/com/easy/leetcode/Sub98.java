package com.easy.leetcode;

/*
98. 验证二叉搜索树
给定一个二叉树，判断其是否是一个有效的二叉搜索树。

假设一个二叉搜索树具有如下特征：

节点的左子树只包含小于当前节点的数。
节点的右子树只包含大于当前节点的数。
所有左子树和右子树自身必须也是二叉搜索树。
示例 1:

输入:
    2
   / \
  1   3
输出: true
示例 2:

输入:
    5
   / \
  1   4
     / \
    3   6
输出: false
解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。
 */

public class Sub98 {
    public static void main(String[] args) {
        Solution_98 solution = new Solution_98();
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(6);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(7);
        System.out.println("输出：" + solution.isValidBST(root));
    }
}

class Solution_98 {
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public boolean isValidBST(TreeNode root, Integer left, Integer right) {
        if (root == null) return true;

        Integer val = root.val;
        if (left != null && val <= left) return false;
        if (right != null && val >= right) return false;

        if (!isValidBST(root.left, left, val)) {
            return false;
        }

        if (!isValidBST(root.right, val, right)) {
            return false;
        }
        return true;
    }
}

