package com.easy.leetcode;

/*
我们提供一个类：

class FooBar {
  public void foo() {
    for (int i = 0; i < n; i++) {
      print("foo");
    }
  }

  public void bar() {
    for (int i = 0; i < n; i++) {
      print("bar");
    }
  }
}
两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。

请设计修改程序，以确保 "foobar" 被输出 n 次。

 

示例 1:

输入: n = 1
输出: "foobar"
解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
示例 2:

输入: n = 2
输出: "foobarfoobar"
解释: "foobar" 将被输出两次。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/print-foobar-alternately
*/
public class Sub1115 {
    public static void main(String[] args) throws InterruptedException {
        //测试用例字符串
        int n = 2;

        //生成结果字符串
        StringBuffer result = new StringBuffer();

        Runnable foo = () -> result.append("foo");
        Runnable bar = () -> result.append("bar");

        FooBar fooBar = new FooBar(n);

        Thread threads[] = new Thread[n];
        Thread fooThread = new FooThread(fooBar, foo);
        Thread barThread = new BarThread(fooBar, bar);

        fooThread.start();
        barThread.start();

        threads[0] = fooThread;
        threads[1] = barThread;

        //等侍所有线程执行完
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        //输出结果串
        System.out.println(result.toString());
    }
}

class FooThread extends Thread {
    FooBar fooBar;
    Runnable runnable;

    public FooThread(FooBar fooBar, Runnable runnable) {
        this.fooBar = fooBar;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            fooBar.foo(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BarThread extends Thread {
    FooBar fooBar;
    Runnable runnable;

    public BarThread(FooBar fooBar, Runnable runnable) {
        this.fooBar = fooBar;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            fooBar.bar(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class FooBar {
    private int n;

    //信号量
    private int flag = 0;

    //定义Object对象为锁
    private Object lock = new Object();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (flag != 0) {
                    lock.wait();
                }
                printFoo.run();
                flag = 1;
                lock.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                while (flag != 1) {
                    lock.wait();
                }

                printBar.run();
                flag = 0;
                lock.notifyAll();
            }
        }
    }
}