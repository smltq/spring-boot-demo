package com.easy.leetcode;

public class Sub1221 {
    public static void main(String[] args) {
        Solution_1221 solution = new Solution_1221();
        String s = "RLRRLLRLRL";
        int count = solution.balancedStringSplit(s);
        System.out.println("最大数量为：" + count);
    }
}

class Solution_1221 {
    public int balancedStringSplit(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        int result = 0, dif = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'L') {
                dif++;
            } else {
                dif--;
            }
            if (dif == 0) {
                result++;
            }
        }
        return result;
    }
}