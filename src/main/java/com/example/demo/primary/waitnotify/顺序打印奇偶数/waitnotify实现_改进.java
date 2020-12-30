package com.example.demo.primary.waitnotify.顺序打印奇偶数;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/30 4:07 下午
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/30 3:11 下午
 * @Description:
 */
public class waitnotify实现_改进 {

    public static void main(String[] args) {
        Print print = new Print();

        PrintQi printQi = new PrintQi(print);
        PrintOu printOu = new PrintOu(print);

        try {
            printQi.start();
            TimeUnit.MILLISECONDS.sleep(10);
            printOu.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    private static class Print {

        private static int i;

        /**
         * true : 打印奇数
         * false : 打印偶数
         */
        private static boolean flag = true;

        /**
         * 和原来的实现的差别在这里，原来的sychronized方法for循环100次，
         * 现在是原来的同步方法中就做一次打印的判断
         */
        public void printQi100() {
            for (int j = 0; j < 100; j++) {
                printQi();
            }
        }

        public void printOu100() {
            for (int j = 0; j < 100; j++) {
                printOu();
            }
        }

        public synchronized void printQi() {
            if (flag) {
                i++;
                System.out.println(Thread.currentThread().getName() + "奇数-----------------" + i);
                flag = false;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /**
             * 这个地方wait被挂起后，重新被唤醒时，就不记得之前的if判断状态了
             */
            System.out.println("============>检验wait是否会走到这里");
        }

        public synchronized void printOu() {
            if (!flag) {
                i++;
                System.out.println(Thread.currentThread().getName() + "偶数=====" + i);
                flag = true;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("============>检验wait是否会走到这里---");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintQi extends Thread {

        private Print print;

        @Override
        public void run() {
            print.printQi100();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintOu extends Thread {

        private Print print;

        @Override
        public void run() {
            print.printOu100();
        }
    }
}

