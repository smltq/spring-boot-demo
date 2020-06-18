package com.easy.leetcode;

/*
1114. 按序打印

我们提供了一个类：

public class Foo {
  public void one() { print("one"); }
  public void two() { print("two"); }
  public void three() { print("three"); }
}
三个不同的线程将会共用一个 Foo 实例。

线程 A 将会调用 one() 方法
线程 B 将会调用 two() 方法
线程 C 将会调用 three() 方法
请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。

 

示例 1:

输入: [1,2,3]
输出: "onetwothree"
解释:
有三个线程会被异步启动。
输入 [1,2,3] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 two() 方法，线程 C 将会调用 three() 方法。
正确的输出是 "onetwothree"。
示例 2:

输入: [1,3,2]
输出: "onetwothree"
解释:
输入 [1,3,2] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 three() 方法，线程 C 将会调用 two() 方法。
正确的输出是 "onetwothree"。
 

注意:

尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。

你看到的输入格式主要是为了确保测试的全面性。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/print-in-order
*/
public class Sub1114 {
    public static void main(String[] args) throws InterruptedException {
        //测试用例字符串
        int[] runOrder = new int[]{2, 3, 1};

        //生成结果字符串
        StringBuffer result = new StringBuffer();

        Runnable one = () -> result.append("one");
        Runnable two = () -> result.append("two");
        Runnable three = () -> result.append("three");

        Foo foo = new Foo();

        Thread threads[] = new Thread[runOrder.length];
        for (int i = 0; i < runOrder.length; ++i) {
            Thread thread = null;
            if (runOrder[i] == 1) {
                thread = new FirstThread(foo, one);
            } else if (runOrder[i] == 2) {
                thread = new SecondThread(foo, two);
            } else if (runOrder[i] == 3) {
                thread = new ThirdThread(foo, three);
            }
            thread.start();
            threads[i] = thread;
        }

        //等侍所有线程执行完
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        //输出结果串
        System.out.println(result.toString());
    }
}

class FirstThread extends Thread {
    Foo foo;
    Runnable runnable;

    public FirstThread(Foo h2o, Runnable runnable) {
        this.foo = h2o;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            foo.first(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SecondThread extends Thread {
    Foo foo;
    Runnable runnable;

    public SecondThread(Foo h2o, Runnable runnable) {
        this.foo = h2o;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            foo.second(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThirdThread extends Thread {
    Foo foo;
    Runnable runnable;

    public ThirdThread(Foo h2o, Runnable runnable) {
        this.foo = h2o;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            foo.third(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Foo {
    //信号量
    private int flag = 0;
    //定义Object对象为锁
    private Object lock = new Object();

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
            //如果flag不为0则让first线程等待，while循环控制first线程如果不满住条件就一直在while代码块中，防止出现中途跳入，执行下面的代码，其余线程while循环同理
            while (flag != 0) {
                lock.wait();
            }

            printFirst.run();
            //定义成员变量为 1
            flag = 1;
            //唤醒其余所有的线程
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            //如果成员变量不为1则让二号等待
            while (flag != 1) {
                lock.wait();
            }

            printSecond.run();
            //如果成员变量为 1 ，则代表first线程刚执行完，所以执行second，并且改变成员变量为 2
            flag = 2;
            //唤醒其余所有的线程
            lock.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
            //如果flag不等于2 则一直处于等待的状态
            while (flag != 2) {
                lock.wait();
            }

            //如果成员变量为 2 ，则代表second线程刚执行完，所以执行third，并且改变成员变量为 0
            printThird.run();
            flag = 0;
            lock.notifyAll();
        }
    }
}