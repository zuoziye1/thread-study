package com.example.demo.juc.atomic.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/20 3:34 下午
 * @Description:
 */
public class ABA问题 {

    static AtomicReference<String> atomicReference = new AtomicReference<>("A");

    public static void main(String[] args){
        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet("A","B"));
            System.out.println(atomicReference.compareAndSet("B", "A"));
        },"t2").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet("A","C")
                    + "\t" + atomicReference.get());
        },"t1").start();
    }
}
