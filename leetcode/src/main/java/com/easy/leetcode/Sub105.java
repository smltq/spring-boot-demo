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
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder, int pre_s, int pre_e, int in_s, int in_e) {
        if (pre_s > pre_e) {
            return null;
        }

        //前序第一个节点为根节点
        TreeNode root = new TreeNode(preorder[pre_s]);

        //找根节点在中序的位置
        int in_root_index = 0;
        for (int i = in_s; i < in_e; i++) {
            if (inorder[i] == preorder[pre_s]) {
                in_root_index = i;
                break;
            }
        }

        int left_tree_size = in_root_index - in_s;

        root.left = buildTree(preorder, inorder, pre_s + 1, pre_s + left_tree_size, in_s, in_root_index - 1);
        root.right = buildTree(preorder, inorder, pre_s + left_tree_size + 1, pre_e, in_root_index + 1, in_e);
        return root;
    }
}