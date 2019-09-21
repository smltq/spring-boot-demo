package com.easy.leetcode;

public class sub538 {
    int sum=0;
    public TreeNode convertBST(TreeNode root) {
        sum=0;
        addSum(root);
        return root;
    }

    public void addSum(TreeNode root){
        if(root!=null){
            addSum(root.right);
            sum+=root.val;
            root.val=sum;
            addSum(root.left);
        }
    }
}
