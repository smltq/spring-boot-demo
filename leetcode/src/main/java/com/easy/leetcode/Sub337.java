package com.easy.leetcode;

/*
337. 打家劫舍 III

在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。

计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。

示例 1:

输入: [3,2,3,null,3,null,1]

     3
    / \
   2   3
    \   \
     3   1

输出: 7
解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
示例 2:

输入: [3,4,5,1,3,null,1]

     3
    / \
   4   5
  / \   \
 1   3   1

输出: 9
解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
 */
public class Sub337 {
    public static void main(String[] args) {
        Solution_337_2 solution = new Solution_337_2();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(1);
        System.out.println("输出：" + solution.rob(root));
    }
}

/**
 * 暴力递归
 * 1个爷爷，2个孩子，4个孙子。
 * max(1个爷爷+4个孙子的钱,2个孩子的钱)
 */
class Solution_337_1 {
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int money = root.val;
        if (root.left != null) {
            money += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
            money += rob(root.right.left) + rob(root.right.right);
        }
        return Math.max(money, rob(root.left) + rob(root.right));
    }
}

/**
 * dp解法
 * 每个节点返回两个值，dp[0]:表示不偷当前节点能拿到的最大钱，dp[1]:表示偷当前节点能拿到的最大钱。
 */
class Solution_337_2 {
    public int rob(TreeNode root) {
        int[] ans = robs(root);
        return Math.max(ans[0], ans[1]);
    }

    public int[] robs(TreeNode root) {
        if (root == null) return new int[2];
        int[] dp = new int[2];
        int[] l = robs(root.left);
        int[] r = robs(root.right);
        //当前节点没有偷，则左孩子和右孩子偷没偷都可以，取它们最大值
        dp[0] = Math.max(l[1], l[0]) + Math.max(r[1], r[0]);
        //当前节点有偷，则左、右孩子不能偷
        dp[1] = l[0] + r[0] + root.val;
        return dp;
    }
}