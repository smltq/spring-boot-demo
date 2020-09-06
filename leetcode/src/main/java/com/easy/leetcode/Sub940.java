package com.easy.leetcode;

import java.util.Arrays;

/*
940. 不同的子序列 II
给定一个字符串 S，计算 S 的不同非空子序列的个数。

因为结果可能很大，所以返回答案模 10^9 + 7.

示例 1：

输入："abc"
输出：7
解释：7 个不同的子序列分别是 "a", "b", "c", "ab", "ac", "bc", 以及 "abc"。
示例 2：

输入："aba"
输出：6
解释：6 个不同的子序列分别是 "a", "b", "ab", "ba", "aa" 以及 "aba"。
示例 3：

输入："aaa"
输出：3
解释：3 个不同的子序列分别是 "a", "aa" 以及 "aaa"。

提示：

S 只包含小写字母。
1 <= S.length <= 2000
 */
public class Sub940 {
    public static void main(String[] args) {
        String s = "aba";
        Solution_940_2 solution = new Solution_940_2();
        System.out.println("返回结果为：" + solution.distinctSubseqII(s));
    }
}

//时间复杂度O(N)
class Solution_940_2 {
    public int distinctSubseqII(String S) {
        int[] last = new int[26];
        Arrays.fill(last, -1);

        int len = S.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        int MOD = 1000000007;
        for (int i = 0; i < len; i++) {
            int x = S.charAt(i) - 'a';
            dp[i + 1] = (dp[i] * 2) % MOD;
            if (last[x] >= 0) {
                dp[i + 1] -= dp[last[x]];
            }
            dp[i + 1] %= MOD;
            last[x] = i;
        }

        dp[len]--;
        if (dp[len] < 0) dp[len] += MOD;
        return dp[len];
    }
}

//时间复杂度O(n^2)
class Solution_940_1 {
    public int distinctSubseqII(String S) {
        long[] dp = new long[26];
        int MOD = 1000000007;
        for (char c : S.toCharArray()) {
            dp[c - 'a'] = (Arrays.stream(dp).sum() + 1) % MOD;
        }
        return (int) (Arrays.stream(dp).sum() % MOD);
    }
}