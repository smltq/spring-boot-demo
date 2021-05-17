package com.easy.leetcode;

/*
993. 二叉树的堂兄弟节点
在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。

如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。

我们给出了具有唯一值的二叉树的根节点 root ，以及树中两个不同节点的值 x 和 y 。

只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true 。否则，返回 false。



示例 1：


输入：root = [1,2,3,4], x = 4, y = 3
输出：false
示例 2：


输入：root = [1,2,3,null,4,null,5], x = 5, y = 4
输出：true
示例 3：



输入：root = [1,2,3,null,4], x = 2, y = 3
输出：false


提示：

二叉树的节点数介于 2 到 100 之间。
每个节点的值都是唯一的、范围为 1 到 100 的整数。

 */
public class Sub993 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(5);

        Solution_993 solution = new Solution_993();
        System.out.println("输出：" + solution.isCousins(root, 4, 5));
    }
}

class Solution_993 {
    int l = 0;

    public boolean isCousins(TreeNode root, int x, int y) {
        l = 0;
        TreeNode xPNode = findTreeNode(root, x, 0);
        int xL = l;

        l = 0;
        TreeNode yPNode = findTreeNode(root, y, 0);
        int yL = l;

        //是不同的父节点并且层级相同，则为堂兄弟节点
        if (xL == yL && xPNode != null && yPNode != null && !xPNode.equals(yPNode)) {
            return true;
        } else {
            return false;
        }
    }

    //查找节点的父节点
    public TreeNode findTreeNode(TreeNode node, int v, int level) {
        if (node == null) {
            return null;
        }
        if (node.left == null && node.right == null) {
            return null;
        }

        if (node.left != null && node.left.val == v) {
            l = level;
            return node;
        } else if (node.right != null && node.right.val == v) {
            l = level;
            return node;
        }

        TreeNode l = findTreeNode(node.left, v, level + 1);
        TreeNode r = findTreeNode(node.right, v, level + 1);
        return l == null ? r : l;
    }
}