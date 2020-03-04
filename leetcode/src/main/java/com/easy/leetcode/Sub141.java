package com.easy.leetcode;

import java.util.*;

/*

给定一个链表，判断链表中是否有环。

为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。

 

示例 1：

输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。

示例 2：

输入：head = [1,2], pos = 0
输出：true
解释：链表中有一个环，其尾部连接到第一个节点。

示例 3：

输入：head = [1], pos = -1
输出：false
解释：链表中没有环。 

进阶：

你能用 O(1)（即，常量）内存解决此问题吗？

 */
public class Sub141 {
    public static void main(String[] args) {
        Solution_141 solution = new Solution_141();

        ListNode root = new ListNode(3);
        root.next = new ListNode(2);
        root.next.next = new ListNode(0);
        root.next.next.next = new ListNode(-4);
        root.next.next.next.next = root.next;

        System.out.println(solution.hasCycle(root));
    }
}

class Solution_141 {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> setNode = new HashSet<>();
        while (head != null) {
            if (setNode.contains(head)) {
                return true;
            } else {
                setNode.add(head);
            }
            head = head.next;
        }
        return false;
    }
}