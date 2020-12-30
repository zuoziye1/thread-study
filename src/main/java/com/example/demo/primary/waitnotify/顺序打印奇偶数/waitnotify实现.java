package com.example.demo.primary.waitnotify.顺序打印奇偶数;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/30 3:11 下午
 * @Description:
 */
public class waitnotify实现 {

    public static void main(String[] args) {
        Print print = new Print();

        PrintQi printQi = new PrintQi(print);
        PrintOu printOu = new PrintOu(print);

        try {
            printQi.start();
            TimeUnit.MILLISECONDS.sleep(10);
            printOu.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 这种方式的实现是把变量放在对象里，而对象是线程共享的，
     * 不用想顺序打印里面写的方法那样，需要在主线程和子线程中传递业务数据。
     * 这里传递了对象的引用，而所有的业务数据都是对象的属性
     */
    private static class Print{
        private static int i ;

        /**
         * true : 打印奇数
         * false : 打印偶数
         */
        private static boolean flag = true ;

        public synchronized void printQi(){
            for (int j = 0 ;j < 100;j++){
                if(flag){
                    i++;
                    System.out.println(Thread.currentThread().getName()+"奇数-----------------"+i);
                    flag = false ;
                    notify();
                }else {
                    try {
                        /**
                         * 为了在最后一次循环后，不再挂起线程，
                         * 否则，会出现没有被唤醒永久挂起
                         */
                        if(j < 99){
                            wait();
                        }
                        /**
                         * 这个地方wait被挂起后，重新被唤醒时，就不记得之前的if判断状态了
                         */
                        System.out.println("=======>验证wait是否被唤醒时失去了if判断状态的记忆");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public synchronized void printOu(){
            for (int j = 0 ;j < 100;j++){
                if(!flag){
                    i++;
                    System.out.println(Thread.currentThread().getName()+"偶数====="+i);
                    flag = true ;
                    notify();
                }else {
                    try {
                        if(j < 99){
                            wait();
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
    private static class PrintQi extends Thread {

        private Print print ;

        @Override
        public void run() {
            print.printQi();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PrintOu extends Thread {

        private Print print ;

        @Override
        public void run() {
            print.printOu();
        }
    }
}
