package com.example.demo.primary.sync;

/**
 * 参考文章：
 * https://blog.csdn.net/javazejian/article/details/72828483#%E7%90%86%E8%A7%A3java%E5%AF%B9%E8%B1%A1%E5%A4%B4%E4%B8%8Emonitor
 *
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
