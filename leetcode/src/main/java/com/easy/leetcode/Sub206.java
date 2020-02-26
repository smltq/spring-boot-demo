package com.easy.leetcode;

import java.util.Stack;

/*

206. 反转链表

反转一个单链表。

示例:

输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
进阶:
你可以迭代或递归地反转链表。你能否用两种方法解决这道题？

 */
public class Sub206 {
    public static void main(String[] args) {
        Solution_206_2 solution = new Solution_206_2();

        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);

        //root = null;
        ListNode result = solution.reverseList(root);
        while (result != null) {
            System.out.print("-->" + result.val);
            result = result.next;
        }
    }
}

/**
 * 方法一，利用栈
 */
class Solution_206_1 {
    public ListNode reverseList(ListNode head) {
        Stack<ListNode> stack = new Stack<>();

        while (head != null) {
            stack.push(head);
            head = head.next;
        }

        ListNode root = stack.isEmpty() ? null : stack.pop();
        ListNode temp = root;
        while (stack.size() > 0) {
            temp.next = stack.pop();
            temp = temp.next;
        }
        if (temp != null) temp.next = null;
        return root;
    }
}

/**
 * 方法二：迭代
 */
class Solution_206_2 {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, current = head;
        while (current != null) {
            ListNode temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        return prev;
    }
}