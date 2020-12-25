package com.example.demo.primary.join;

/**
 * join()
 *
 * 使用示例：https://www.cnblogs.com/lcplcpjava/p/6896904.html
 * 实现原理：https://juejin.cn/post/6844903997472505864
 *
 */
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        JoinThread joinThread1 = new JoinThread();

        // Thread[Thread-1,5,main] :
        // Thread-1 代表当前线程的名字（如果你没有命名，系统默认从0开始0、1、2、3。。。，中间的5代表优先级，第三个main表示线程所处的线程组）
        JoinThread joinThread2 = new JoinThread();

        joinThread1.start();
        Long start = System.currentTimeMillis();

        /**
         * if (millis == 0) {
         *             while (isAlive()) {
         *                 wait(0);
         *             }
         *         }
         *
         * 源码里面的核心代码是这里，这里方法调用省略了调用方this,this即这里的 joinThread1
         * 这个对象的锁被释放了，那么就会阻塞在wait这里，只有重新获取了锁，才能继续往下进行。而jvm做了
         * notify的操作，使得join方法结束后，wait的对象的锁可以重新获取，那么也就可以继续往下进行
         *
         *
         * 这里注意：joinThread1对象在执行它的run方法是不需要对这个对象的锁，因为它也没有同步的需求，没有synchronized同步
         */
        joinThread1.join();
        Long end = System.currentTimeMillis();
        System.out.println("---------------"+(end-start));
        joinThread2.start();
    }
}
