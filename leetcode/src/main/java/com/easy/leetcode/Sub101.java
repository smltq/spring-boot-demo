package com.easy.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/*
101. 对称二叉树
给定一个二叉树，检查它是否是镜像对称的。

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

    1
   / \
  2   2
 / \ / \
3  4 4  3
但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

    1
   / \
  2   2
   \   \
   3    3

说明:

如果你可以运用递归和迭代两种方法解决这个问题，会很加分。
 */
public class Sub101 {
    public static void main(String[] args) {
        Solution_101_2 solution = new Solution_101_2();

        TreeNode root = new TreeNode(1);

        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);

        root.right = new TreeNode(2);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        System.out.println("是否镜像对称：" + solution.isSymmetric(root));
    }
}

/**
 * 递归 实现
 */
class Solution_101_1 {
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 == null || root2 == null) {
            return false;
        } else {
            return (root1.val == root2.val) && isMirror(root1.left, root2.right) && isMirror(root1.right, root2.left);
        }
    }
}

/**
 * 迭代 实现
 */
class Solution_101_2 {
    public boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            if (t1 == null && t2 == null) {
                continue;
            } else if (t1 == null || t2 == null) {
                return false;
            } else {
                if (t1.val == t2.val) {
                    queue.add(t1.left);
                    queue.add(t2.right);
                    queue.add(t1.right);
                    queue.add(t2.left);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
