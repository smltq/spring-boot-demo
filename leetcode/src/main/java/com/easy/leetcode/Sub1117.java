package com.easy.leetcode;

public class Sub1117 {
    public static void main(String[] args) throws InterruptedException {
        String test = "HOHOHHOOHOHHHHHOHHHOH";
        //System.out.println("当前数组长度：" + test.length());
        H2O_1 h2o = new H2O_1();
        for (int i = 0; i < test.length(); ++i) {
            //System.out.println("当前索引值：" + i);
            if (test.charAt(i) == 'O') {
                h2o.oxygen(new OThread());
                //new H2O_1().oxygen(new OThread());
            } else if (test.charAt(i) == 'H') {
                h2o.hydrogen(new HThread());
                //new H2O_1().hydrogen(new HThread());
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

class H2O_1 {
    public H2O_1() {}
    int h = 0;
    public synchronized void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while (h == 2) {
            wait();
        }
        releaseHydrogen.run();
        ++h;
        notify();
    }

    public synchronized void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while (h < 2) {
            System.out.println("\t氢气\t" + h);
            wait();
        }
        releaseOxygen.run();
        h = 0;
        notify();
    }
}