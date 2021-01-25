package com.example.demo.juc.atomic.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参考文章：https://www.jianshu.com/p/8e74009684c7
 *
 * @Author: 姚飞虎
 * @Date: 2021/1/20 3:11 下午
 * @Description:
 */
public class CAS初识 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5,50));
        System.out.println(atomicInteger.compareAndSet(5,100));

        System.out.println(atomicInteger.getAndSet(200));

        /**
         * 查看自增是实现原理，是用了自选加Unsafe类提供的原子操作
         */
        System.out.println(atomicInteger.getAndIncrement());
    }
}
