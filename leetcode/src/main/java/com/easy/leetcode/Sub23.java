package com.easy.leetcode;

/*
23. 合并K个升序链表
给你一个链表数组，每个链表都已经按升序排列。
请你将所有链表合并到一个升序链表中，返回合并后的链表。

示例 1：

输入：lists = [[1,4,5],[1,3,4],[2,6]]
输出：[1,1,2,3,4,4,5,6]
解释：链表数组如下：
[
  1->4->5,
  1->3->4,
  2->6
]
将它们合并到一个有序链表中得到。
1->1->2->3->4->4->5->6
示例 2：

输入：lists = []
输出：[]
示例 3：

输入：lists = [[]]
输出：[]


提示：

k == lists.length
0 <= k <= 10^4
0 <= lists[i].length <= 500
-10^4 <= lists[i][j] <= 10^4
lists[i] 按 升序 排列
lists[i].length 的总和不超过 10^4
 */
public class Sub23 {
    public static void main(String[] args) {
        Solution_23_1 solution = new Solution_23_1();
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1);
        lists[0].next = new ListNode(4);
        lists[0].next.next = new ListNode(5);

        lists[1] = new ListNode(1);
        lists[1].next = new ListNode(3);
        lists[1].next.next = new ListNode(4);

        lists[2] = new ListNode(2);
        lists[2].next = new ListNode(6);

        System.out.println("返回结果为：" + solution.mergeKLists(lists));
    }
}

//分治合并，时间复杂度O（lon k*n)
class Solution_23_2 {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return merge(lists, 0, lists.length - 1);
    }

    //链表俩俩合并
    public ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }
        int mid = l + (r - l) / 2;
        ListNode lNode = merge(lists, l, mid);
        ListNode rNode = merge(lists, mid + 1, r);
        return merge(lNode, rNode);
    }

    //递归合并两个有序链表
    public ListNode merge(ListNode l, ListNode r) {
        if (l == null) return r;
        if (r == null) return l;
        if (l.val < r.val) {
            l.next = merge(l.next, r);
            return l;
        }
        r.next = merge(l, r.next);
        return r;
    }
}

//逐条合并,时间复杂度O（k*n)
class Solution_23_1 {
    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        ListNode ans = null;
        for (int i = 0; i < len; i++) {
            ans = merge(ans, lists[i]);
        }
        return ans;
    }

    public ListNode merge(ListNode l, ListNode r) {
        if (l == null) return r;
        if (r == null) return l;
        ListNode ans = new ListNode(0);
        ListNode head = ans;
        while (l != null && r != null) {
            if (l.val < r.val) {
                ans.next = l;
                l = l.next;
            } else {
                ans.next = r;
                r = r.next;
            }
            ans = ans.next;
        }
        if (l == null) ans.next = r;
        if (r == null) ans.next = l;
        return head.next;
    }
}