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
        Solution_19_2 solution = new Solution_19_2();
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);
        System.out.println("返回结果为：" + solution.removeNthFromEnd(root, 2));
    }
}

//双指针，时间复杂度为O（n）
class Solution_19_2 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p = head;
        ListNode q = head;
        for (int i = 0; i <= n; i++) {
            if (p == null) {
                if (n == 1) {
                    return null;
                } else {
                    return head.next;
                }
            }
            p = p.next;
        }
        while (p != null) {
            p = p.next;
            q = q.next;
        }
        q.next = q.next.next;
        return head;
    }
}

//普通解法，遍历两次列表，时间复杂度O(2*N)
class Solution_19_1 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode root = head;
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        head = root;
        //处理只有一个节点的情况
        if (count == 1 && n == 1) {
            return null;
        }
        count = count - n;
        //处理删除节点是首节点的情况
        if (count == 0) {
            root = head.next;
            return root;
        }
        //查找节点并做删除操作
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