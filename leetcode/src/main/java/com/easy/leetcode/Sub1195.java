package com.easy.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/*
编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：

如果这个数字可以被 3 整除，输出 "fizz"。
如果这个数字可以被 5 整除，输出 "buzz"。
如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。

假设有这么一个类：

class FizzBuzz {
  public FizzBuzz(int n) { ... }               // constructor
  public void fizz(printFizz) { ... }          // only output "fizz"
  public void buzz(printBuzz) { ... }          // only output "buzz"
  public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
  public void number(printNumber) { ... }      // only output the numbers
}
请你实现一个有四个线程的多线程版  FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：

线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/fizz-buzz-multithreaded
*/
public class Sub1195 {
    public static void main(String[] args) throws InterruptedException {
        int n = 15;

        List<String> result = new ArrayList<>();

        Runnable fizz = () -> result.add("fizz");
        Runnable buzz = () -> result.add("buzz");
        Runnable fizzbuzz = () -> result.add("fizzbuzz");

        IntConsumer intConsumer = (x) -> result.add(Integer.toString(x));

        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threads[] = new Thread[4];
        threads[0] = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(fizzbuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threads[1] = new Thread(() -> {
            try {
                fizzBuzz.fizz(fizz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threads[2] = new Thread(() -> {
            try {
                fizzBuzz.buzz(buzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threads[3] = new Thread(() -> {
            try {
                fizzBuzz.number(intConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        //等侍所有线程执行完
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        //输出结果串
        System.out.println(String.join(",", result));
    }
}

class FizzBuzz {
    private int n;

    private int i = 1;

    private Object lock = new Object();

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        while (i <= n) {
            synchronized (lock) {
                if (i > n) break;
                if ((i % 3 == 0) && (i % 15 != 0)) {
                    printFizz.run();
                    i++;
                }
                lock.notifyAll();
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (i <= n) {
            synchronized (lock) {
                if (i > n) break;
                if ((i % 5 == 0) && (i % 15 != 0)) {
                    printBuzz.run();
                    i++;
                }
                lock.notifyAll();
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (i <= n) {
            synchronized (lock) {
                if (i > n) break;
                if (i % 15 == 0) {
                    printFizzBuzz.run();
                    i++;
                }
                lock.notifyAll();
            }
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        while (i <= n) {
            synchronized (lock) {
                if (i > n) break;
                if ((i % 5 != 0) && (i % 3 != 0)) {
                    printNumber.accept(i);
                    i++;
                }
                lock.notifyAll();
            }
        }
    }
}