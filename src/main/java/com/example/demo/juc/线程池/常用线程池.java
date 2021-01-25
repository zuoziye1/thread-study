package com.example.demo.juc.线程池;


import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class 常用线程池 {

    @Test
    public void test1(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            System.out.println(Thread.currentThread().getName()+"----------1");
        });

        executorService.execute(()->{
            System.out.println(Thread.currentThread().getName()+"----------2");
        });

        Executors.newScheduledThreadPool(4);

    }

}
