package com.example.demo.生产者消费者.waitnotify通信;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 最简单的生产者消费者模式
 *
 * 思路分析：
 * 生产者生产时，首先需要先拿到仓库的锁，然后才能生产
 * 消费者消费时，也是必须拿到仓库的锁，同时还要看是否有产品剩余，有才能消费
 *
 * @Author: 姚飞虎
 * @Date: 2020/12/26 6:22 下午
 * @Description:
 */
public class WaitNotify的生产消费者模式 {

    private static List<String> lockObject = new ArrayList();

    public static void main(String[] args) {
        Consumer consumer1 = new Consumer(lockObject);
        Consumer consumer2 = new Consumer(lockObject);
        Productor productor = new Productor(lockObject);
        consumer1.start();
        consumer2.start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productor.start();
    }


    static class Consumer extends Thread {
        private List<String> lock;

        public Consumer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                synchronized (lock) {
                    try {
                        //这里使用if的话，就会存在wait条件变化造成程序错误的问题
                        if (lock.isEmpty()) {
                            System.out.println(Thread.currentThread().getName() + " list为空");
                            System.out.println(Thread.currentThread().getName() + " 调用wait方法");
                            lock.wait();
                            System.out.println(Thread.currentThread().getName() + "  wait方法结束");
                        }
                        /**
                         * 如果这个地方没有判空，就会出现空指针，出现的原因是
                         * 上面wait被唤醒时，并不会记住原来的判空状态，会一行一行代码往后执行，
                         * 直接跳出 if (lock.isEmpty()) {} ，而此时可能会出现，刚生产的产品被多个消费者
                         * 的某个消费者消费了，但是其他的消费者线程也被唤醒，也走到了这个逻辑，就会出现仓库里面没有数据
                         * ，却还是来消费了。数组越界错误。
                         */
                        if(!lock.isEmpty()){
                            String element = lock.remove(0);
                            System.out.println(Thread.currentThread().getName() + " 取出第一个元素为：" + element);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }


    static class Productor extends Thread {
        private List<String> lock;

        public Productor(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " 开始添加元素");
                    lock.add(Thread.currentThread().getName());
                    lock.notifyAll();
                }
            }
        }

    }

}

