package com.example.demo.juc.aqs;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/20 12:36 下午
 * @Description:
 */
public class 自旋锁之MCS {

    class Node {
        volatile Node next;//后继节点
        //默认false
        volatile boolean locked;
    }

    /**
     * 指向最后加入的线程
     */
    final AtomicReference<自旋锁之MCS.Node> tail = new AtomicReference<>(null);

    ThreadLocal<Node> current;

    public 自旋锁之MCS() {
        //初始化当前节点的node
        current = new ThreadLocal<自旋锁之MCS.Node>() {
            @Override
            protected 自旋锁之MCS.Node initialValue() {
                return new 自旋锁之MCS.Node();
            }
        };
    }


    public void lock() throws InterruptedException {

        //获取当前线程的Node
        Node own = current.get();

        //获取前驱节点，然后原子的将tail设置为当前线程的节点
        Node preNode = tail.getAndSet(own);

        //如果前驱节点不为null，说明有线程已经占用
        if (preNode != null) {
            //设置当前节点为需要占用状态；
            own.locked = true;
            //把前面节点的next指向自己
            preNode.next = own;

            //在自己的节点上自旋等待前驱通知
            while (own.locked) {

                System.out.println(Thread.currentThread().getName() + " 开始自旋....  ");
                Thread.sleep(2000);

            }

        }

        System.out.println(Thread.currentThread().getName() + " 获得了锁....  ");

    }


    public void unlock() {
        //获取自己的节点
        Node own = current.get();
        //
        if (own.next == null) {
            //判断自身是不是最后一个线程
            if (tail.compareAndSet(own, null)) {
                //是的话就结束
                return;
            }

            //在判断过程中，又有线程进来
            while (own.next == null) {

            }

        }
        //本身解锁，通知它的后继节点可以工作了，不用再自旋了
        own.next.locked = false;
        // for gc
        own.next = null;


    }

    public static void main(String[] args) {

        自旋锁之MCS lock = new 自旋锁之MCS();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "  获得锁 ");
                    //前驱释放，do own work
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getName() + "  释放锁 ");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };

        Thread t1 = new Thread(runnable, "线程1");
        Thread t2 = new Thread(runnable, "线程2");
        Thread t3 = new Thread(runnable, "线程3");

        t1.start();
        t2.start();
        t3.start();

    }


}