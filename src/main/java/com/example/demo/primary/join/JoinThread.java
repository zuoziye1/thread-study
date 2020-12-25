package com.example.demo.primary.join;

import java.time.LocalDateTime;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/24 6:07 下午
 * @Description:
 */
public class JoinThread extends Thread{

    @Override
    public void run(){
        try {
            System.out.println(LocalDateTime.now()+"====="+Thread.currentThread().getName()+"线程体---->开始...");
            Thread.sleep(5000);
            System.out.println(LocalDateTime.now()+"====="+Thread.currentThread().getName()+"线程体");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
