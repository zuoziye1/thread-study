package com.example.demo.primary.waitnotify.顺序打印ABC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/30 10:27 上午
 * @Description:
 */
public class waitnotify错误实现 {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        ThreadA threadA = new ThreadA(b, c);
        ThreadB threadB = new ThreadB(a, c);
        ThreadC threadC = new ThreadC(b, a);

        threadA.start();
        threadB.start();
        threadC.start();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadA extends Thread{

        private Object b ;

        private Object c ;

        @Override
        public void run() {
            while (true){
                synchronized (c){
                    synchronized (b){
                        System.out.println("-------->aaaaaaa");
                    }
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadB extends Thread{

        private Object a ;

        private Object c ;

        @Override
        public void run() {
            while (true){
                synchronized (a){
                    synchronized (c){
                        System.out.println("-------->bbbbbbbb");
                    }
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThreadC extends Thread{

        private Object b ;

        private Object a ;

        @Override
        public void run() {
            while (true){
                synchronized (b){
                    synchronized (a){
                        System.out.println("-------->ccccccccc");
                    }
                }
            }
        }
    }
}
