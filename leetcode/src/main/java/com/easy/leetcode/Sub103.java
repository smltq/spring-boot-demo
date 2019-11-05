package com.easy.leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/*
给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。

例如：
给定二叉树 [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
返回锯齿形层次遍历如下：

[
  [3],
  [20,9],
  [15,7]
]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal
 */
public class Sub103 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.left = new TreeNode(4);
//        root.right.right = new TreeNode(5);

        Solution103_1 solution = new Solution103_1();
        List<List<Integer>> list = solution.zigzagLevelOrder(root);
        for (List<Integer> subList : list) {
            System.out.println(subList.toString());
        }
    }
}

class Solution103_1 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        return BFS(root);
    }

    List<List<Integer>> BFS(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        List<List<Integer>> result = new LinkedList<>();
        boolean reverse = true;
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> subResult = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node;
                if (reverse) {
                    node = deque.pollFirst();
                    if (node.left != null) {
                        deque.addLast(node.left);
                    }
                    if (node.right != null) {
                        deque.addLast(node.right);
                    }
                } else {
                    node = deque.pollLast();
                    if (node.right != null) {
                        deque.addFirst(node.right);
                    }
                    if (node.left != null) {
                        deque.addFirst(node.left);
                    }
                }
                subResult.add(node.val);
            }
            result.add(subResult);
            reverse = !reverse;
        }
        return result;
    }
}