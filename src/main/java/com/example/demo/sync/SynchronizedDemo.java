package com.example.demo.sync;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/14 12:24 下午
 * @Description:
 */
public class SynchronizedDemo {

    public void method() {
        synchronized (this) {
            System.out.println("Method 1 start");
        }
    }

}
