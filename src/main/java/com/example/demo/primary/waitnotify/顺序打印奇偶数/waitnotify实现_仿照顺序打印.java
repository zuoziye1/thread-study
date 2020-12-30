package com.example.demo.primary.waitnotify.顺序打印奇偶数;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/30 11:58 上午
 * @Description:
 */
public class waitnotify实现_仿照顺序打印 {

    public static void main(String[] args) {
        Object ji = new Object();
        Object ou = new Object();

        PrintQi printQi = new PrintQi( ou, ji);
        PrintOu printOu = new PrintOu( ji, ou);
        try {
            printQi.start();
            TimeUnit.MILLISECONDS.sleep(10);
            printOu.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintQi extends Thread{

        private Object ou ;

        private Object ji ;

        @Override
        public void run() {
            int limit = 10 ;
            int count = 0 ;
            int start = -1 ;
            while (count < limit){
                synchronized (ou){
                    synchronized (ji){
                        start = start + 2 ;
                        System.out.println("奇数：-------"+start);
                        count++;
                        ji.notify();
                    }
                    try {
                        if(count < limit){
                            ou.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintOu extends Thread{

        private Object ji ;

        private Object ou ;

        @Override
        public void run() {
            int start = 0 ;
            int limit = 10 ;
            int count = 0 ;
            while (count < limit){
                synchronized (ji){
                    synchronized (ou){
                        start += 2 ;
                        System.out.println("偶数：-------"+start);
                        count++;
                        ou.notify();
                    }
                    try {
                        if(count < limit){
                            ji.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
