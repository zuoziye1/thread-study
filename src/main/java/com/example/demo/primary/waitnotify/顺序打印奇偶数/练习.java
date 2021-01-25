package com.example.demo.primary.waitnotify.顺序打印奇偶数;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/25 7:38 下午
 * @Description:
 */
public class 练习 {

    /**
     * 思路：奇数线程只打印奇数，如果不是，就阻塞，等待偶数线程唤醒
     * 偶数线程也是同样的思路，二者要交替进行
     */
    public static void main(String[] args) throws InterruptedException {
        Print print = new Print();

        /**
         * print 就是同一个对象锁，就是竞争的对象
         */
        PrintQi printQi = new PrintQi(print);
        PrintOu printOu = new PrintOu(print);

        printQi.start();
        TimeUnit.MILLISECONDS.sleep(2);
        printOu.start();

    }

    private static class Print {

        private static volatile int i;

        /**
         * true : 打印奇数
         */
        private static boolean flag = true ;

        public synchronized void qi() {
            if (flag) {
                System.out.println(Thread.currentThread().getName()+"--奇数:\t" + ++i);
                flag = false ;
                notify();
            } else {
                try {
                    if(i < 98){
                        wait();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void ou() {
            if (!flag) {
                System.out.println(Thread.currentThread().getName()+"--偶数:\t" + ++i);
                flag = true ;
                notify();
            } else {
                try {
                    if(i < 99){
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintQi extends Thread{

        private Print print ;

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                print.qi();
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintOu extends Thread{

        private Print print ;

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                print.ou();
            }
        }
    }
}
