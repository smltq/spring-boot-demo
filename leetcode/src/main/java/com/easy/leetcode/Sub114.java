package com.easy.leetcode;

import java.util.Stack;

/*
114. 二叉树展开为链表
给定一个二叉树，原地将它展开为链表。

例如，给定二叉树

    1
   / \
  2   5
 / \   \
3   4   6
将其展开为：

1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6
 */
public class Sub114 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        Solution_114_2 solution = new Solution_114_2();
        solution.flatten(root);
        outTree(root);
    }

    //中序输出
    static void outTree(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + "\t");
            outTree(root.left);
            outTree(root.right);
        }
    }
}

/**
 * 递归-后序遍历
 */
class Solution_114_1 {
    TreeNode pre = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.right);
        flatten(root.left);

        root.right = pre;
        root.left = null;

        pre = root;
    }
}

/**
 * 迭代
 */
class Solution_114_2 {
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode pre = null;

        while (!stack.isEmpty()) {
            TreeNode temp = stack.pop();
            if (pre != null) {
                pre.right = temp;
                pre.left = null;
            }

            if (temp.right != null) {
                stack.push(temp.right);
            }
            if (temp.left != null) {
                stack.push(temp.left);
            }
            pre = temp;
        }
    }
}