package com.easy.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
234. 回文链表
请判断一个链表是否为回文链表。

示例 1:

输入: 1->2
输出: false
示例 2:

输入: 1->2->2->1
输出: true
进阶：
你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */
public class Sub234 {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(2);
        root.next.next.next = new ListNode(1);
        Solution_234 solution = new Solution_234();
        System.out.println("是否为回文链表：" + solution.isPalindrome(root));
    }
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution_234 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        List<Integer> arrVal = new ArrayList<>();
        ListNode root = head;
        while (root != null) {
            arrVal.add(root.val);
            root = root.next;
        }
        int len = arrVal.size();
        int mid = len % 2 == 1 ? (len / 2 + 1) : (len / 2);
        for (int i = 0; i < mid; i++) {
            if (!arrVal.get(i).equals(arrVal.get(len - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}