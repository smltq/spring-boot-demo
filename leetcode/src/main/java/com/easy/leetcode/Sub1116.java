package com.easy.leetcode;

import java.util.function.IntConsumer;

/*
假设有这么一个类：

class ZeroEvenOdd {
  public ZeroEvenOdd(int n) { ... }      // 构造函数
  public void zero(printNumber) { ... }  // 仅打印出 0
  public void even(printNumber) { ... }  // 仅打印出 偶数
  public void odd(printNumber) { ... }   // 仅打印出 奇数
}
相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程：

线程 A 将调用 zero()，它只输出 0 。
线程 B 将调用 even()，它只输出偶数。
线程 C 将调用 odd()，它只输出奇数。
每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。

 

示例 1：

输入：n = 2
输出："0102"
说明：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
示例 2：

输入：n = 5
输出："0102030405"

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/print-zero-even-odd
*/
public class Sub1116 {
    public static void main(String[] args) throws InterruptedException {
        //数量
        int n = 2;
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(n);

        IntConsumer intConsumer=new IntConsumer();

        Thread zeroThread = new ZeroThread(zeroEvenOdd, intConsumer);
        Thread evenThread = new EvenThread(zeroEvenOdd, intConsumer);
        Thread oddThread = new OddThread(zeroEvenOdd, intConsumer);
    }
}

class ZeroThread extends Thread {
    ZeroEvenOdd zeroEvenOdd;
    IntConsumer printNumber;

    public ZeroThread(ZeroEvenOdd zeroEvenOdd, IntConsumer printNumber) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.printNumber = printNumber;
    }

    @Override
    public void run() {
        try {
            zeroEvenOdd.zero(printNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EvenThread extends Thread {
    ZeroEvenOdd zeroEvenOdd;
    IntConsumer printNumber;

    public EvenThread(ZeroEvenOdd zeroEvenOdd, IntConsumer printNumber) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.printNumber = printNumber;
    }

    @Override
    public void run() {
        try {
            zeroEvenOdd.even(printNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class OddThread extends Thread {
    ZeroEvenOdd zeroEvenOdd;
    IntConsumer printNumber;

    public OddThread(ZeroEvenOdd zeroEvenOdd, IntConsumer printNumber) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.printNumber = printNumber;
    }

    @Override
    public void run() {
        try {
            zeroEvenOdd.odd(printNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ZeroEvenOdd {
    private int n;

    //生产0的数量
    private int zeroCount = 0;

    //信号量
    private int flag = 0;

    //定义Object对象为锁
    private Object lock = new Object();

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    //生成0
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (flag == 0) {
                    lock.wait();
                }
                printNumber.accept(0);
                flag = 0;
                zeroCount++;
                lock.notifyAll();
            }
        }
    }

    //生产偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            synchronized (lock) {
                while (flag == 2 || zeroCount % 2 == 1) {
                    lock.wait();
                }
                printNumber.accept(i);
                flag = 2;
                lock.notifyAll();
            }
        }
    }

    //生产奇数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            synchronized (lock) {
                while (flag == 1 || zeroCount % 2 == 0) {
                    lock.wait();
                }
                printNumber.accept(i);
                flag = 1;
                lock.notifyAll();
            }
        }
    }
}