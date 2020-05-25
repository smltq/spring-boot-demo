package com.easy.leetcode;

/*
105. 从前序与中序遍历序列构造二叉树
根据一棵树的前序遍历与中序遍历构造二叉树。

注意:
你可以假设树中没有重复的元素。

例如，给出

前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
返回如下的二叉树：

    3
   / \
  9  20
    /  \
   15   7
 */
public class Sub105 {
    public static void main(String[] args) {
        Solution_105 solution = new Solution_105();
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        outTree(solution.buildTree(preorder, inorder));
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

class Solution_105 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return null;
    }
}