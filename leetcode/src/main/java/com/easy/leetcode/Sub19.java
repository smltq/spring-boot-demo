package com.easy.leetcode;

/*
19. 删除链表的倒数第N个节点
给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

示例：

给定一个链表: 1->2->3->4->5, 和 n = 2.

当删除了倒数第二个节点后，链表变为 1->2->3->5.
说明：

给定的 n 保证是有效的。

进阶：

你能尝试使用一趟扫描实现吗？
 */
public class Sub19 {
    public static void main(String[] args) {
        Solution_19 solution = new Solution_19();
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);
        System.out.println("返回结果为：" + solution.removeNthFromEnd(root, 2));
    }
}

class Solution_19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode root = head;
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        head = root;
        if (count == 1 && n == 1) {
            return null;
        }
        count = count - n;
        if (count == 0) {
            root = head.next;
            return root;
        }
        while (head.next != null) {
            if (count == 1) {
                head.next = head.next.next;
                break;
            }
            head = head.next;
            count--;
        }
        return root;
    }
}