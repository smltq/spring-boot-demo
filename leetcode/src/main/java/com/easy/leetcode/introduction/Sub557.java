package com.easy.leetcode.introduction;

/*
    557. 反转字符串中的单词 III
    给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。



    示例：

    输入："Let's take LeetCode contest"
    输出："s'teL ekat edoCteeL tsetnoc"


    提示：

    在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
 */
public class Sub557 {
    public static void main(String[] args) {
        Solution_557 solution = new Solution_557();
        String s = "Let's take LeetCode contest";
        String ans = solution.reverseWords(s);
        System.out.println("结果：" + ans);
    }
}

class Solution_557 {
    public String reverseWords(String s) {
        String[] ss = s.split(" ");
        StringBuffer ans = new StringBuffer();
        for (String str : ss) {
            ans.append(reverseString(str.toCharArray()) + " ");
        }
        return ans.toString().trim();
    }

    public String reverseString(char[] s) {
        int len = s.length;
        int l = 0, r = len - 1;
        while (l < r) {
            char temp = s[l];
            s[l] = s[r];
            s[r] = temp;
            l++;
            r--;
        }
        return new String(s);
    }
}
