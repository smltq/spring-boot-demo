package com.easy.leetcode.introduction;

import com.easy.leetcode.ListNode;

/*
    876. 链表的中间结点
    给定一个头结点为 head 的非空单链表，返回链表的中间结点。

    如果有两个中间结点，则返回第二个中间结点。

    示例 1：

    输入：[1,2,3,4,5]
    输出：此列表中的结点 3 (序列化形式：[3,4,5])
    返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
    注意，我们返回了一个 ListNode 类型的对象 ans，这样：
    ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
    示例 2：

    输入：[1,2,3,4,5,6]
    输出：此列表中的结点 4 (序列化形式：[4,5,6])
    由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。

    提示：

    给定链表的结点数介于 1 和 100 之间。

 */
public class Sub876 {
    public static void main(String[] args) {
        Solution_876 solution = new Solution_876();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
//        head.next.next.next.next.next = new ListNode(6);

        ListNode ans = solution.middleNode(head);
        System.out.println("结果：" + ans.val);
    }
}

class Solution_876 {
    public ListNode middleNode(ListNode head) {
        int len = 0;
        ListNode temp = head;
        while (temp != null) {
            temp = temp.next;
            len++;
        }
        int n = len / 2;
        while (n > 0) {
            head = head.next;
            n--;
        }
        return head;
    }
}
