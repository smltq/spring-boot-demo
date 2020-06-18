package com.easy.leetcode;

import java.util.*;

/*
102. 二叉树的层序遍历
给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。

例如:
给定二叉树: [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
返回其层次遍历结果：

[
  [3],
  [9,20],
  [15,7]
]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal
 */
public class Sub102 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        Solution102_2 solution = new Solution102_2();
        List<List<Integer>> list = solution.levelOrder(root);
        for (List<Integer> subList : list) {
            System.out.println(subList.toString());
        }
    }
}

/**
 * 广搜+队列
 */
class Solution102_1 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        return BFS(root);
    }

    List<List<Integer>> BFS(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<List<Integer>> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> subResult = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                subResult.add(node.val);
            }
            result.add(subResult);
        }
        return result;
    }
}

/**
 * 深搜+递归
 */
class Solution102_2 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<List<Integer>> result = new LinkedList<>();

        DFS(root, result, 1);
        return result;
    }

    void DFS(TreeNode node, List<List<Integer>> result, int level) {
        if (result.size() < level) {
            result.add(new LinkedList<>());
        }
        result.get(level - 1).add(node.val);
        if (node.left != null) {
            DFS(node.left, result, level + 1);
        }
        if (node.right != null) {
            DFS(node.right, result, level + 1);
        }
    }
}