package com.example.demo.primary.等待线程结束;

/**
 * activeCount 方法一般用来测试使用，测试当所有的子线程处理完后，回归主线程。
 *
 * @Author: 姚飞虎
 * @Date: 2020/12/29 11:45 上午
 * @Description:
 */
public class activeCount使用 {

    /**
     * 验证volatile不是线程安全的
     */
    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT;
             i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000;
                         i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }
        //等待所有累加线程都结束
        /**
         * 这里就是在 【activeCount方法】中提到的，run时，有两个线程在活跃，debug时，只有一个线程在活跃
         */
        while (Thread.activeCount() > 2) {
            Thread.yield();
            System.out.println(race);
        }
    }
}