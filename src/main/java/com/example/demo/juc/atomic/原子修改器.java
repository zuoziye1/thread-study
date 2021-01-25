package com.example.demo.juc.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater 原子修改器
 *
 * 参考：https://www.cnblogs.com/doit8791/p/9098116.html
 * @Author: 姚飞虎
 * @Date: 2021/1/19 4:25 下午
 * @Description:
 */
public class 原子修改器 {

    public static void main(String[] args) throws Exception {
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Dog.class, String.class, "name");
        Dog dog1 = new Dog();
        System.out.println(updater.compareAndSet(dog1, "dog1", "compareAndSet"));
        System.out.println(dog1.name);
        System.out.println(updater.getAndSet(dog1, "getAndSet"));
        System.out.println(dog1.name);
    }

    private static class Dog {
        volatile String name = "dog1";
    }
}
