package com.example.demo.primary.等待线程结束;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/29 11:36 上午
 * @Description:
 */
public class activeCount方法 {

    /**
     * Monitor Ctrl-Break这个线程只会在IDEA中被打印出来
     * 在IDEA中通过debug启动的不会出现，只有run启动的会出现
     * 所以在idea中执行run的时候这个要写成2而不能是1在eclipse
     * 中却不会出现这个问题，这是软件引起的在开发调试过程要注意。
     */
    public static void main(String[] args) {
        // 此方法返回活动线程的当前线程的线程组中的数量
        System.out.println(Thread.activeCount());
        System.out.println("----------------");
        // 打印当前线程组中的所有的线程
        Thread.currentThread().getThreadGroup().list();
    }
}
