package com.easy.leetcode;

public class sub538 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(2);
        root.right = new TreeNode(13);

        Solution solution=new Solution();
        solution.convertBST(root);
        outTree(root);
    }

    //中序输出
    static void outTree(TreeNode root){
        if(root!=null) {
            System.out.print(root.val+"\t");
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

class Solution {
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        addSum(root);
        return root;
    }

    public void addSum(TreeNode root) {
        if (root != null) {
            addSum(root.right);
            sum += root.val;
            root.val = sum;
            addSum(root.left);
        }
    }
}
