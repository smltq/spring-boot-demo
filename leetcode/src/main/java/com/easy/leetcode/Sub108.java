package com.easy.leetcode;

import java.util.Stack;

/*
108. 将有序数组转换为二叉搜索树

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
        //数组最大索引值入栈
        stack.add(nums.length - 1);
        //数组最小索引值入栈
        stack.add(0);

        Stack<TreeNode> tree = new Stack<TreeNode>();
        TreeNode root = new TreeNode(0);
        //随便new树节点入栈
        tree.add(root);

        while (!stack.isEmpty()) {
            int left = stack.pop();
            int right = stack.pop();
            //求出中间节点索引值
            int mid = left + (right - left) / 2;
            TreeNode node = tree.pop();
            //更新根节点值
            node.val = nums[mid];

            //计算左叶子节点最大最小索引值
            int r = mid - 1, l = left;
            //如果存在左叶子节点
            if (l <= r) {
                node.left = new TreeNode(0);
                //随便new个树节点入栈
                tree.add(node.left);

                //对应右索引值入栈
                stack.push(r);
                //对应左索引值入栈
                stack.push(l);
            }

            //计算右节点最大最小索引值
            l = mid + 1;
            r = right;
            if (l <= r) {
                node.right = new TreeNode(0);
                //随便new个树节点入栈
                tree.add(node.right);

                //对应右索引值入栈
                stack.push(r);
                //对应左索引值入栈
                stack.add(l);
            }
        }
        return root;
    }
}