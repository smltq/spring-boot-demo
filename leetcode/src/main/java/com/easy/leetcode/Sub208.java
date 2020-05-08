package com.easy.leetcode;

/*
208. 实现 Trie (前缀树)
实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。

示例:

Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // 返回 true
trie.search("app");     // 返回 false
trie.startsWith("app"); // 返回 true
trie.insert("app");
trie.search("app");     // 返回 true
说明:

你可以假设所有的输入都是由小写字母 a-z 构成的。
保证所有输入均为非空字符串。
 */

public class Sub208 {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");

        System.out.println(trie.search("apple"));   // 返回 true

        System.out.println(trie.search("app"));     // 返回 false

        System.out.println(trie.startsWith("app"));        // 返回 true

        trie.insert("app");

        System.out.println(trie.search("app"));     // 返回 true
    }
}

class Trie {
    private final int ALPHABET_SIZE = 26;
    private Trie[] children = new Trie[ALPHABET_SIZE];
    boolean isEndOfWord = false;

    public Trie() {
    }

    public void insert(String word) {
        Trie tmp = this;
        for (char i : word.toCharArray()) {
            if (tmp.children[i - 'a'] == null) {
                tmp.children[i - 'a'] = new Trie();
            }
            tmp = tmp.children[i - 'a'];
        }
        tmp.isEndOfWord = true;
    }

    public boolean search(String word) {
        Trie tmp = this;
        for (char i : word.toCharArray()) {
            if (tmp.children[i - 'a'] == null) {
                return false;
            }
            tmp = tmp.children[i - 'a'];
        }
        return tmp.isEndOfWord ? true : false;
    }

    public boolean startsWith(String prefix) {
        Trie tmp = this;
        for (char i : prefix.toCharArray()) {
            if (tmp.children[i - 'a'] == null) {
                return false;
            }
            tmp = tmp.children[i - 'a'];
        }
        return true;
    }
}