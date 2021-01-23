package com.example.demo.primary.waitnotify.顺序打印ABC;

import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/14 11:07 上午
 * @Description:
 */
public class 练习 {

    public static void main(String[] args) throws InterruptedException {
        正常();
    }

    public static void 正常() throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        /**
         * 其实感觉这里也会有死锁的可能，因为开始在A线程持有C，然后等待A时，B线程可能也运行了，他会先持有A，然后等待B
         * 但是线程C也可以持有B等待C，那么就互相等待了
         *
         * 而如果加入了睡眠，则理论上会使得他们依次进入线程就绪状态，大概率会依次进入执行状态，
         * 而之后的话，他们就进入了先后的牵制状态，可以按照三个线程依次执行，分析下锁的状态，就不会进入死锁状态了。
         */
        new Print("A", c, a).start();
        TimeUnit.MILLISECONDS.sleep(10);
        new Print("B", a, b).start();
        TimeUnit.MILLISECONDS.sleep(10);
        new Print("C", b, c).start();
    }

    public static void 也会出现死锁() {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        /**
         * 其实感觉这里也会有死锁的可能，但是没有复现出来
         */
        for (int i = 0; i < 10; i++) {
            new Print("A", c, a).start();
            new Print("B", a, b).start();
            new Print("C", b, c).start();
        }
    }

    @AllArgsConstructor
    private static class Print extends Thread {

        private String name;

        private Object pre;

        private Object curr;

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (pre) {
                    synchronized (curr) {
                        if (name.equals("C")) {
                            System.out.print(name + "\n");
                        } else {
                            if (name.equals("A")) {
                                System.out.print("line:" + i + "\t----\t" + name + "-->");
                            } else {
                                System.out.print(name + "-->");
                            }
                        }
                        curr.notify();
                    }
                    try {
                        // 释放pre锁,阻塞当前线程
                        if (i < 9) {
                            pre.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
