package com.example.demo.primary.中断线程;

/**
 * 怎么终止一个线程
 *
 * 参考自己的总结：
 * https://blog.csdn.net/myloveinnocence/article/details/112528090
 *
 * @Author: 姚飞虎
 * @Date: 2021/1/12 11:36 上午
 * @Description:
 */
public class 中断线程 {

    public static void main(String[] args) throws InterruptedException {
        try {
            InterruptService interruptService = new InterruptService();
            Thread thread = new Thread(interruptService);
            thread.start();
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static class InterruptService implements Runnable {
        //如果使用Callable接口中call方法异常就会得以扩散
        @Override
        public void run() {
            try {
                System.out.println( "begin run" );
                //重要：无论是先中断还是先阻塞都能达到停止线程的目的，只要两者配置使用就可以到达效果
                // 这里的中断只是设置了中断标志位，而无法真正的中断线程。除非和以下三种方式结合
                /**
                 *  * <p> If this thread is blocked in an invocation of the {@link
                 *      * Object#wait() wait()}, {@link Object#wait(long) wait(long)}, or {@link
                 *      * Object#wait(long, int) wait(long, int)} methods of the {@link Object}
                 *      * class, or of the {@link #join()}, {@link #join(long)}, {@link
                 *      * #join(long, int)}, {@link #sleep(long)}, or {@link #sleep(long, int)},
                 *      * methods of this class, then its interrupt status will be cleared and it
                 *      * will receive an {@link InterruptedException}.
                 *
                 *      这里说明了在经历阻塞方法时，中断状态就被清空了,同时还要抛出一个中断异常
                 */
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().isInterrupted());



                //方式一，线程进入sleep
                Thread.sleep( 10 );
                //方式二、join
//            Thread.currentThread().join();
                //方式三、wait
//            Thread.currentThread().wait();
                System.out.println( "begin end" );
            } catch (Exception e) {
                /**
                 * * Tests whether the current thread has been interrupted.  The
                 *      * <i>interrupted status</i> of the thread is cleared by this method.  In
                 *      * other words, if this method were to be called twice in succession, the
                 *      * second call would return false (unless the current thread were
                 *      * interrupted again, after the first call had cleared its interrupted
                 *      * status and before the second call had examined it).
                 */
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.currentThread().isInterrupted());

                System.out.println("先interrupt再阻塞后终止了");
                e.printStackTrace();
            }
        }
    }
}
