package com.easy.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sub1117 {
    public static void main(String[] args) throws InterruptedException {
        String test = "HOHOHHOOHOHHHHHOHHHOH";
        H2O h2o = new H2O();
        for (int i = 0; i < test.length(); ++i) {
            if (test.charAt(i) == 'O') {
                h2o.oxygen(new Runnable() {
                    public void run() {
                        //System.out.print(test.charAt(i));
                    }
                });
            } else if (test.charAt(i) == 'H') {

            }
        }
    }
}

class H2O {
    private int wH, wO;
    private Lock lock;
    private Condition waitingH, waitingO, barrier;
    private int aH, aO;

    public H2O() {
        wH = 0;
        wO = 0;
        aH = 0;
        aO = 0;
        lock = new ReentrantLock();
        waitingH = lock.newCondition();
        waitingO = lock.newCondition();
        barrier = lock.newCondition();
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        lock.lock();
        try {
            wH++;
            // while not allowed to leave
            while (aH == 0) {
                if (wH >= 2 && wO >= 1) {
                    if (aO > 0) {
                        barrier.await();
                    }
                    wH -= 2;
                    aH += 2;
                    wO -= 1;
                    aO += 1;
                    waitingH.signal();
                    waitingO.signal();
                } else {
                    waitingH.await();
                    barrier.signal();
                }
            }
            aH--;
        } finally {
            releaseHydrogen.run();
            lock.unlock();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        lock.lock();
        try {
            wO++;

            while (aO == 0) {
                if (wH >= 2 && wO >= 1) {
                    if (aH > 0) {
                        barrier.await();
                    }
                    wH -= 2;
                    aH += 2;
                    wO -= 1;
                    aO += 1;
                    waitingH.signal();
                    waitingH.signal();
                } else {
                    waitingO.await();
                    barrier.signal();
                }
            }
            aO--;
        } finally {
            releaseOxygen.run();
            lock.unlock();
        }

    }
}