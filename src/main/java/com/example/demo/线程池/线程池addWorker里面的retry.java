package com.example.demo.线程池;

/**
 * retry: 的说明
 * https://blog.csdn.net/u011381576/article/details/79975852
 *
 * @Author: 姚飞虎
 * @Date: 2021/1/12 7:15 下午
 * @Description:
 */
public class 线程池addWorker里面的retry {

    public static void main(String[] args) {
        线程池addWorker里面的retry tmp = new 线程池addWorker里面的retry();
        tmp.testRetry();
    }

    public void testRetry() {
        int i = 0;
        retry:  //①
        while (true) {
            i++;
            System.out.println("i=" + i);
            int j = 0;
//            retry:   //②
            for (; ; ) {
                j++;
                System.out.println("j=" + j);
                if (j == 2) {
                    break retry;
                }
            }
        }
    }

    public void testRetry1() {
        int i = 0;
//        retry:  //①
        while (true) {
            i++;
            System.out.println("i=" + i);
            int j = 0;
            retry:   //②
            for (; ; ) {
                j++;
                System.out.println("j=" + j);
                if (j == 2) {
                    break retry;
                }
            }
        }
    }
}
