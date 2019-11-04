package com.easy.leetcode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
5 个沉默寡言的哲学家围坐在圆桌前，每人面前一盘意面。叉子放在哲学家之间的桌面上。（5 个哲学家，5 根叉子）

所有的哲学家都只会在思考和进餐两种行为间交替。哲学家只有同时拿到左边和右边的叉子才能吃到面，而同一根叉子在同一时间只能被一个哲学家使用。每个哲学家吃完面后都需要把叉子放回桌面以供其他哲学家吃面。只要条件允许，哲学家可以拿起左边或者右边的叉子，但在没有同时拿到左右叉子时不能进食。

假设面的数量没有限制，哲学家也能随便吃，不需要考虑吃不吃得下。

设计一个进餐规则（并行算法）使得每个哲学家都不会挨饿；也就是说，在没有人知道别人什么时候想吃东西或思考的情况下，每个哲学家都可以在吃饭和思考之间一直交替下去。

哲学家从 0 到 4 按 顺时针 编号。请实现函数 void wantsToEat(philosopher, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork)：

philosopher 哲学家的编号。
pickLeftFork 和 pickRightFork 表示拿起左边或右边的叉子。
eat 表示吃面。
putLeftFork 和 pickRightFork 表示放下左边或右边的叉子。
由于哲学家不是在吃面就是在想着啥时候吃面，所以思考这个方法没有对应的回调。
给你 5 个线程，每个都代表一个哲学家，请你使用类的同一个对象来模拟这个过程。在最后一次调用结束之前，可能会为同一个哲学家多次调用该函数。

 

示例：

输入：n = 1
输出：[[4,2,1],[4,1,1],[0,1,1],[2,2,1],[2,1,1],[2,0,3],[2,1,2],[2,2,2],[4,0,3],[4,1,2],[0,2,1],[4,2,2],[3,2,1],[3,1,1],[0,0,3],[0,1,2],[0,2,2],[1,2,1],[1,1,1],[3,0,3],[3,1,2],[3,2,2],[1,0,3],[1,1,2],[1,2,2]]
解释:
n 表示每个哲学家需要进餐的次数。
输出数组描述了叉子的控制和进餐的调用，它的格式如下：
output[i] = [a, b, c] (3个整数)
- a 哲学家编号。
- b 指定叉子：{1 : 左边, 2 : 右边}.
- c 指定行为：{1 : 拿起, 2 : 放下, 3 : 吃面}。
如 [4,2,1] 表示 4 号哲学家拿起了右边的叉子。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/the-dining-philosophers
*/
public class Sub1226 {
    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        StringBuffer result = new StringBuffer();
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
        Thread threads[] = new Thread[n];
        for (int i = 0; i < n; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                try {
                    Runnable pickLeftFork = () -> result.append(finalI + "11");
                    Runnable pickRightFork = () -> result.append(finalI + "21");
                    Runnable eat = () -> result.append(finalI + "03");
                    Runnable putLeftFork = () -> result.append(finalI + "12");
                    Runnable putRightFork = () -> result.append(finalI + "22");

                    diningPhilosophers.wantsToEat(finalI, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }

        //等侍所有线程执行完
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out.println(result.toString() + "长度为：" + result.length());

        char charArr[] = result.toString().toCharArray();
        System.out.print("[");
        for (int i = 0; i < charArr.length; i += 3) {
            System.out.print("[");
            System.out.print(charArr[i] + "," + charArr[i + 1] + "," + charArr[i + 2]);
            System.out.print("],");
        }
        System.out.print("]");
    }
}

class DiningPhilosophers {
    private Lock[] locks = {new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock()};

    public DiningPhilosophers() {

    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        int leftFork = philosopher;
        int rightFork = (philosopher + 4) % 5;

        //0,2,4左手先拿
        if (philosopher % 2 == 0) {
            locks[leftFork].lock();
            //拿起左边的叉子
            pickLeftFork.run();

            locks[rightFork].lock();
            //拿起右边的叉子
            pickRightFork.run();
        }
        //1,3,5右手先拿(避免和邻居相同的方向拿筷子，造成死锁)
        else {
            locks[rightFork].lock();
            //拿起右边的叉子
            pickRightFork.run();

            locks[leftFork].lock();
            //拿起左边的叉子
            pickLeftFork.run();
        }

        //吃意大利面
        eat.run();

        //放下左边的叉子
        putLeftFork.run();
        locks[leftFork].unlock();

        //放下右边的叉子
        putRightFork.run();
        locks[rightFork].unlock();
    }
}