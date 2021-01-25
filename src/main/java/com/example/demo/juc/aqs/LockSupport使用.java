package com.example.demo.juc.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport类可以阻塞当前线程以及唤醒指定被阻塞的线程。
 * 主要是通过park()和unpark(thread)方法来实现阻塞和唤醒线程的操作的。
 * <p>
 * 每个线程都有一个许可(permit)，permit只有两个值1和0,默认是0。
 * <p>
 * 1.当调用unpark(thread)方法，就会将thread线程的许可permit设置成1(注意多次调用unpark方法，不会累加，permit值还是1)。
 * 2.当调用park()方法，如果当前线程的permit是1，那么将permit设置为0，并立即返回。
 * 如果当前线程的permit是0，那么当前线程就会阻塞，直到别的线程将当前线程的permit设置为1.park方法会将permit再次设置为0，并返回。
 * <p>
 * 注意：因为permit默认是0，所以一开始调用park()方法，线程必定会被阻塞。调用unpark(thread)方法后，会自动唤醒thread线程，即park方法立即返回。
 * <p>
 * 语义理解：https://www.cnblogs.com/zhizhizhiyuan/p/4966827.html
 *
 * 本文代码参考：https://www.cnblogs.com/zhaoxinshanwei/p/7053537.html
 */
public class LockSupport使用 {

    public static void main(String[] args) throws Exception {
        test4();
    }

    /**
     * 当前线程被阻塞
     */
    public static void test1() {
        LockSupport.park();
        System.out.println("block.");
    }

    /**
     * 先给了线程许可
     */
    public static void test2() {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);//释放许可
        LockSupport.park();// 获取许可
        System.out.println("b");
    }

    /**
     * LockSupport 不可重入
     */
    public static void test3() {
        Thread thread = Thread.currentThread();

        LockSupport.unpark(thread);

        System.out.println("a");
        LockSupport.park();
        System.out.println("b");
        LockSupport.park(); // 会阻塞在这里
        System.out.println("c");
    }

    /**
     * park可以响应中断，但是不抛出异常
     *
     * 可见park被唤醒有两种方式：
     * 1. unpark
     * 2. 响应中断
     *
     * @throws Exception
     */
    public static void test4() throws Exception {

        Thread t = new Thread(new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = 0;

                while ((end - start) <= 1000) {
                    count++;
                    end = System.currentTimeMillis();
                }

                System.out.println("after 1 second.count=" + count);

                //等待获取许可
                LockSupport.park();
                System.out.println("thread over." + Thread.currentThread().isInterrupted());

            }
        });

        t.start();

        Thread.sleep(2000);

        // 中断线程
        t.interrupt();

        System.out.println("main over");
    }
}