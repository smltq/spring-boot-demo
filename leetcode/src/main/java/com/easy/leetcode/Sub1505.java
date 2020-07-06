package com.easy.leetcode;

/*
1505. 最多 K 次交换相邻数位后得到的最小整数
给你一个字符串 num 和一个整数 k 。其中，num 表示一个很大的整数，字符串中的每个字符依次对应整数上的各个 数位 。

你可以交换这个整数相邻数位的数字 最多 k 次。

请你返回你能得到的最小整数，并以字符串形式返回。



示例 1：



输入：num = "4321", k = 4
输出："1342"
解释：4321 通过 4 次交换相邻数位得到最小整数的步骤如上图所示。
示例 2：

输入：num = "100", k = 1
输出："010"
解释：输出可以包含前导 0 ，但输入保证不会有前导 0 。
示例 3：

输入：num = "36789", k = 1000
输出："36789"
解释：不需要做任何交换。
示例 4：

输入：num = "22", k = 22
输出："22"
示例 5：

输入：num = "9438957234785635408", k = 23
输出："0345989723478563548"


提示：

1 <= num.length <= 30000
num 只包含 数字 且不含有 前导 0 。
1 <= k <= 10^9
 */
public class Sub1505 {
    public static void main(String[] args) {
        Solution_1505 solution = new Solution_1505();
        String num = "4321";
        int k = 4;
        String result = solution.minInteger(num, k);
        System.out.println("返回结果：" + result);
    }
}

class Solution_1505 {
    public String minInteger(String num, int k) {
        char[] arr = num.toCharArray();
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            int minIndex = i;
            int end = i + k + 1 > len ? len : i + k;
            for (int j = i + 1; j < end; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            for (int l = minIndex; l > i; l--) {
                char t = arr[l];
                arr[l] = arr[l - 1];
                arr[l - 1] = t;
                k--;
                if (k == 0) {
                    return String.valueOf(arr);
                }
            }
        }
        return String.valueOf(arr);
    }
}
