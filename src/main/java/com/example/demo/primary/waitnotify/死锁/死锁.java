package com.example.demo.primary.waitnotify.死锁;

import lombok.AllArgsConstructor;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/14 11:37 上午
 * @Description:
 */
public class 死锁 {

    public static void main(String[] args) {
        String a = "a" ;
        String b = "b" ;

        for (int i = 0; i < 1000; i++) {
            new SiDemo(a,b).start();
            new SiDemo(b,a).start();
        }

    }

    @AllArgsConstructor
    private static class SiDemo extends Thread{

        private Object pre ;
        private Object self ;

        @Override
        public void run() {
            synchronized (pre){
                synchronized (self){
                    System.out.println("---");
                }
            }
        }
    }
}
