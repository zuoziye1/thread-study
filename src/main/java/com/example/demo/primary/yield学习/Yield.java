package com.example.demo.primary.yield学习;

import lombok.AllArgsConstructor;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/28 5:09 下午
 * @Description:
 */
public class Yield {

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1("线程1");
        Thread1 thread2 = new Thread1("线程2");
        Thread1 thread3 = new Thread1("线程3");
        Thread1 thread4 = new Thread1("线程4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    @AllArgsConstructor
    private static class Thread1 extends Thread{

        private String name ;

        @Override
        public void run() {
            System.out.println("执行"+Thread.currentThread().getName()+"的线程体");
            for(int i = 0; i< 100 ; i++){
                System.out.println(Thread.currentThread().getName()+"======================="+i);
                yield();
            }
        }
    }
}
