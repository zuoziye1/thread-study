package com.example.demo.primary.volatile学习;

/**
 * volatile变量自增运算测试 **@author zzm
 */
public class Volatile是线程不安全的_synchronized同步 {

    public static int race = 0;

    /**
     * 虽然race 没有使用 volatile 进行可见性的设置，
     * 但是synchronized 本身就保证了可见性，Lock 也可以，
     * 由于上锁的粒度比较大
     * 因此，这里并不会出现缓存不一致而导致的其他cpu的处理覆盖以处理某个值的cpu的处理动作
     *
     * 因此，这也是没问题的
     */
    public static synchronized void increase() {
        race++;
        System.out.println(race+"++");
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        System.out.println("----------->");
        for (int i = 0; i < THREADS_COUNT * 10; i++) {
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

