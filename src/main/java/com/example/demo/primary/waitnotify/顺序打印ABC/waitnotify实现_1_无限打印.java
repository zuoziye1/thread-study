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
public class waitnotify实现_1_无限打印 {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        try {
            ThreadA threadA = new ThreadA(c, a);
            /**
             * 这里之所以要加上一个睡眠，是因为start只是让线程进入了就绪状态，而并非进入了running状态
             */
            threadA.start();
            TimeUnit.MILLISECONDS.sleep(10);
            ThreadB threadB = new ThreadB(a, b);
            threadB.start();
            TimeUnit.MILLISECONDS.sleep(10);
            ThreadC threadC = new ThreadC(b, c);
            threadC.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadA extends Thread{

        private Object prev ;

        private Object self ;

        @Override
        public void run() {
            while (true){
                /**
                 * 一个线程想要执行业务逻辑，那么必须先获取前一个线程的self对象的锁，即，要等一个线程执行
                 * 完了，才轮到它。
                 * 同时，在自己执行完后，应当让当前线程被阻塞，释放prev的所有权，因为，当前线程结束后，轮到了
                 * 下一个要打印字母的线程
                 */
                synchronized (prev){
                    synchronized (self){
                        System.out.print("A->");
                        self.notify();
                    }
                    try {
                        prev.wait();
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

        private Object prev ;

        private Object self ;

        @Override
        public void run() {
            while (true){
                synchronized (prev){
                    synchronized (self){
                        System.out.print("B->");
                        self.notify();
                    }
                    try {
                        prev.wait();
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

        private Object prev ;

        private Object self ;

        @Override
        public void run() {
            while (true){
                synchronized (prev){
                    synchronized (self){
                        System.out.print("C\n");
                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
