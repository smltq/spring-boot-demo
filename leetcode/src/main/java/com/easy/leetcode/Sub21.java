package com.easy.leetcode;

/*
21. 合并两个有序链表

将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

示例：

输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 */
public class Sub21 {
    public static void main(String[] args) {
        Solution_21 solution = new Solution_21();
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        ListNode root = solution.mergeTwoLists(list1, list2);
        System.out.println("合并两个有序链表结果为：");
        do {
            System.out.print(root.val + "->");
            root = root.next;
        } while (root != null);
    }
}

class Solution_21 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode t = root;
        while (l1 != null || l2 != null) {
            if (l2 == null) {
                t = t.next = new ListNode(l1.val);
                l1 = l1.next;
            } else if (l1 == null) {
                t = t.next = new ListNode(l2.val);
                l2 = l2.next;
            } else if (l1.val <= l2.val) {
                t = t.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                t = t.next = new ListNode(l2.val);
                l2 = l2.next;
            }
        }
        return root.next;
    }
}