package com.example.demo.primary.sync;

/**
 * 演示 synchronized 的工作方式
 * @Author: 姚飞虎
 * @Date: 2020/12/24 4:37 下午
 * @Description:
 */
public class Synchronized示例 implements Runnable {

    /**
     * 共享资源(临界资源)
     */
    static int i = 0;

    /**
     * synchronized 修饰实例方法
     * 因为是实例方法，要获实例锁，才能进行访问。如果是对于同一个实例对象，那么不论有多少线程，
     * 因为有锁的加持，所以，总是串行的。这也把i++这段代码包装成了原子的。
     */
    public synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Synchronized示例 instance = new Synchronized示例();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
    /**
     * 输出结果:
     * 2000000
     */
}
