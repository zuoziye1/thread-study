package com.example.demo.primary.volatile学习;


/**
 * volatile变量自增运算测试 **@author zzm
 */
public class Volatile是线程不安全的 {

    public static volatile int race = 0;

    public static void increase() {
        race++;
        System.out.println(race+"++");
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        System.out.println("----------->");
        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(
                    ()->{
                        for (int j = 0; j < 10000; j++) {
                            increase();
                        }
                    }
            ).start();
        }
        //等待所有累加线程都结束
        int count = 0 ;
        while ( (count = Thread.activeCount()) > 2 ){
            System.out.println("当前活跃线程数："+count);
            Thread.yield();
        }
        System.out.println(race);
    }
}
