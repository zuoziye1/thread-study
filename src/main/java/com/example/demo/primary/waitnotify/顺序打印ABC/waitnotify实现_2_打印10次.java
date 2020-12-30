package com.example.demo.primary.waitnotify.顺序打印ABC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/30 11:18 上午
 * @Description:
 */
public class waitnotify实现_2_打印10次 {

    public static void main(String[] args) {
        int limit = 10 ;

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        try {
            ThreadA threadA = new ThreadA(limit,c, a);
            threadA.start();
            TimeUnit.MILLISECONDS.sleep(10);
            ThreadB threadB = new ThreadB(limit,a, b);
            threadB.start();
            TimeUnit.MILLISECONDS.sleep(10);
            ThreadC threadC = new ThreadC(limit,b, c);
            threadC.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadA extends Thread{

        int limit ;

        private Object prev ;

        private Object self ;

        @Override
        public void run() {
            int count = 0 ;
            while (count < limit){
                synchronized (prev){
                    synchronized (self){
                        System.out.print("A->");
                        count++;
                        self.notify();
                    }
                    try {
                        /**
                         * 倘若这里没有进行判断，就会出现第10次打印后，依然执行了wait，而之后又没有其他地方进行唤醒
                         * 则进入了永久等待。
                         */
                        if(count < limit){
                            prev.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadB extends Thread{

        int limit ;

        private Object prev ;

        private Object self ;

        @Override
        public void run() {
            int count = 0 ;
            while (count < limit){
                synchronized (prev){
                    synchronized (self){
                        System.out.print("B->");
                        count++;
                        self.notify();
                    }
                    try {
                        if(count < limit){
                            prev.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadC extends Thread{

        int limit ;

        private Object prev ;

        private Object self ;

        @Override
        public void run() {
            int limit = 10 ;
            int count = 0 ;
            while (count < limit){
                synchronized (prev){
                    synchronized (self){
                        System.out.print("C\n");
                        count++;
                        self.notify();
                    }
                    try {
                        if(count < limit){
                            prev.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}

