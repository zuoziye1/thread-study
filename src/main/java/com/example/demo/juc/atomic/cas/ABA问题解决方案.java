package com.example.demo.juc.atomic.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/20 3:37 下午
 * @Description:
 */
public class ABA问题解决方案 {

    static AtomicStampedReference<String> atomicReference = new AtomicStampedReference<>("A", 1);

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);// 睡一秒，让t1线程拿到最初的版本号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * atomicReference.getStamp 时间戳：相当于是个版本号
             *
             * 这就是个乐观锁的处理方式，用 检查当前要修改的内存值，即它原本的版本号，修改成功则改变版本号
             */
            System.out.println(atomicReference.compareAndSet("A", "B",
                    atomicReference.getStamp(), atomicReference.getStamp() + 1));

            System.out.println(atomicReference.compareAndSet("B", "A",
                    atomicReference.getStamp(), atomicReference.getStamp() + 1));
        }, "t2").start();

        new Thread(() -> {
            // Returns the current value of the stamp.
            int stamp = atomicReference.getStamp();//拿到最开始的版本号
            try {
                TimeUnit.SECONDS.sleep(3);// 睡3秒，让t2线程的ABA操作执行完
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet("A", "C", stamp, stamp + 1));

            // 如果使用下面这样，令stamp = stamp + 2 则能成功，因为最后变为A时，先是A变为B,B的版本号是stamp+1,然后将B变为A,
            // 在B的版本号上又加了1，因此，再变回A时，A的版本号就变成了 stamp = stamp + 2
//            System.out.println(atomicReference.compareAndSet("A", "C", stamp+2, stamp + 1));
        }, "t1").start();

    }
}
