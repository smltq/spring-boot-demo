package com.easy.leetcode;

import java.util.Stack;

/*
将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。

本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。

示例:

给定有序数组: [-10,-3,0,5,9],

一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：

      0
     / \
   -3   9
   /   /
 -10  5

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree
 */
public class Sub108 {
    public static void main(String[] args) {
        int[] nums = new int[]{-10, -3, 0, 5, 9};

        Solution108_2 solution = new Solution108_2();
        TreeNode root = solution.sortedArrayToBST(nums);
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

//二分+递归实现
class Solution108_1 {
    public TreeNode sortedArrayToBST(int[] nums) {
        return convertToBST(nums, 0, nums.length - 1);
    }

    TreeNode convertToBST(int[] nums, int begin, int end) {
        if (begin > end) return null;
        //取中值
        int mid = begin + (end - begin) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        //左叶子树
        root.left = convertToBST(nums, begin, mid - 1);
        //右叶子树
        root.right = convertToBST(nums, mid + 1, end);
        return root;
    }
}

//非递归实现
class Solution108_2 {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        Stack<Integer> stack = new Stack<Integer>();
        stack.add(nums.length - 1);
        stack.add(0);

        Stack<TreeNode> tree = new Stack<TreeNode>();
        TreeNode root = new TreeNode(0);
        tree.add(root);

        while (!stack.isEmpty()) {
            int left = stack.pop();
            int right = stack.pop();
            int mid = left + (right - left) / 2;
            TreeNode node = tree.pop();
            node.val = nums[mid];
            int r = mid - 1, l = left;
            if (l <= r) {
                node.left = new TreeNode(0);
                tree.add(node.left);
                stack.push(r);
                stack.push(l);
            }
            l = mid + 1;
            r = right;
            if (l <= r) {
                node.right = new TreeNode(0);
                tree.add(node.right);
                stack.push(r);
                stack.add(l);
            }
        }
        return root;
    }
}