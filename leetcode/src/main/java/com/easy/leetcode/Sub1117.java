package com.easy.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sub1117 {
    public static void main(String[] args) throws InterruptedException {
        String test = "HOHOHHOOHOHHHHHOHHHOH";
        System.out.println("当前数组长度：" + test.length());
        for (int i = 0; i < test.length(); ++i) {
            //System.out.println("当前索引值：" + i);
            if (test.charAt(i) == 'O') {
                new H2O_2().oxygen(new OThread());
            } else if (test.charAt(i) == 'H') {
                new H2O_2().hydrogen(new HThread());
            }
        }
    }
}

//生产氢气
class HThread implements Runnable {
    public void run() {
        System.out.print("H");
    }
}

//生产氧气
class OThread implements Runnable {
    public void run() {
        System.out.print("O");
    }
}

class H2O_2 {
    private int flagH = 0;
    private int flagO = 0;
    private Object lock = new Object();

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        synchronized (lock) {
            if (flagO * 2 >= flagH) {
                flagH++;
                releaseHydrogen.run();
            } else {
                releaseHydrogen.wait();
            }
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        synchronized (lock) {
            if (flagO == 0 || flagO * 2 <= flagH) {
                flagO++;
                releaseOxygen.run();
            } else {
                releaseOxygen.wait();
            }
        }
    }
}

class H2O_1 {
    private int flagH = 0;
    private int flagO = 0;
    private ReentrantLock lock;
    private Condition condition;

    public H2O_1() {
        lock = new ReentrantLock(true);
        condition = lock.newCondition();
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while (true) {
            lock.lock();
            boolean b = flagH < 2;
            if (b) {
                releaseHydrogen.run();
                flagH++;
                condition.signalAll();
                lock.unlock();
                break;
            } else {
                condition.signalAll();
                condition.await();
            }
            lock.unlock();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while (true) {
            lock.lock();
            boolean b = flagO == 0 || flagO * 2 <= flagH;
            if (b) {
                releaseOxygen.run();
                flagO++;
                condition.signalAll();
                lock.unlock();
                break;
            } else {
                condition.signalAll();
                condition.await();
            }
            lock.unlock();
        }
    }
}