package com.easy.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
94. 二叉树的中序遍历
给定一个二叉树，返回它的中序 遍历。

示例:

输入: [1,null,2,3]
   1
    \
     2
    /
   3

输出: [1,3,2]
进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
public class Sub94 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        Solution_94 solution = new Solution_94();
        System.out.println("输出：" + solution.inorderTraversal(root));
    }
}

class Solution_94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        inorder(ans, root);
        return ans;
    }

    /**
     * 中序遍历
     */
    public void inorder(List<Integer> ans, TreeNode root) {
        if (root == null) return;
        inorder(ans, root.left);
        ans.add(root.val);
        inorder(ans, root.right);
    }
}
