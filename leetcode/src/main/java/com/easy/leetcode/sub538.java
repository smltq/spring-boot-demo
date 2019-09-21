package com.easy.leetcode;

import java.util.Stack;

/*
给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。

例如：

输入: 二叉搜索树:
              5
            /   \
           2     13

输出: 转换为累加树:
             18
            /   \
          20     13

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/convert-bst-to-greater-tree
 */
public class sub538 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(2);
        root.right = new TreeNode(13);
        //root.right.right = new TreeNode(14);
        //root.right.left = new TreeNode(11);

        Solution2 solution = new Solution2();
        solution.convertBST(root);
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

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

//递归实现
class Solution1 {
    public TreeNode convertBST(TreeNode root) {
        addSum(root, 0);
        return root;
    }

    public int addSum(TreeNode node, int parentVal) {
        //如果没有节点了，返回父节点值
        if (node == null) {
            return parentVal;
        }

        //累加右边所有节点值
        int rVal = addSum(node.right, parentVal);

        //当前节点值=右边所有节点累加值+当前节点值
        node.val += rVal;
        //System.out.println("当前节点值：" + node.val);

        //累加左边所有节点值
        int lVal = addSum(node.left, node.val);
        return lVal;
    }
}

//利用堆栈，去递归化，中序遍历
class Solution2 {
    public TreeNode convertBST(TreeNode root) {
        TreeNode oRoot = root;
        Stack<TreeNode> stack = new Stack();
        int sum = 0;
        while (true) {
            //右节点入栈
            if (root != null) {
                stack.push(root);
                root = root.right;
            } else {
                TreeNode node = stack.pop();
                sum += node.val;
                node.val = sum;
                root = node.left;
            }
            //堆栈计算完成
            if (stack.empty()) {
                break;
            }
        }
        return oRoot;
    }
}
