package com.easy.leetcode;

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
public class sub108 {
    public static void main(String[] args) {
        int[] vals = new int[]{-10, -3, 0, 5, 9};

        Solution solution = new Solution();
        TreeNode root = solution.sortedArrayToBST(vals);
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
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return convertToBST(nums, 0, nums.length - 1);
    }

    TreeNode convertToBST(int[] vals, int begin, int end) {
        if (begin > end) return null;
        int mid = begin + (end - begin) / 2;
        TreeNode root = new TreeNode(vals[mid]);
        root.left = convertToBST(vals, begin, mid - 1);
        root.right = convertToBST(vals, mid + 1, end);
        return root;
    }
}