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
public class Sub108 {
    public static void main(String[] args) {
        int[] nums = new int[]{-10, -3, 0, 5, 9};

        Solution108 solution = new Solution108();
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
class Solution108 {
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