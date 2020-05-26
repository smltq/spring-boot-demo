package com.easy.leetcode;

/*
148. 排序链表

在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。

示例 1:

输入: 4->2->1->3
输出: 1->2->3->4
示例 2:

输入: -1->5->3->4->0
输出: -1->0->3->4->5
 */
public class Sub148 {
    public static void main(String[] args) {
        Solution_148 solution = new Solution_148();
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);
        //head.next.next.next.next = new ListNode(5);

        head = solution.sortList(head);
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }
}

/**
 * 归并排序
 */
class Solution_148 {
    public ListNode sortList(ListNode head) {
        //只有一个元素或空节点时，递归结束
        if (head == null || head.next == null) {
            return head;
        }

        //找中间节点，拆分成两个链表
        ListNode fast = head, slow = head, pre_slow = head;
        while (fast != null && fast.next != null) {
            pre_slow = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        pre_slow.next = null;

        ListNode l = sortList(head);
        ListNode r = sortList(slow);

        //合并有序链表
        ListNode ans = new ListNode(0), temp = ans;
        while (l != null && r != null) {
            if (l.val > r.val) {
                temp.next = r;
                r = r.next;
            } else {
                temp.next = l;
                l = l.next;
            }
            temp = temp.next;
        }
        temp.next = l == null ? r : l;
        return ans.next;
    }
}