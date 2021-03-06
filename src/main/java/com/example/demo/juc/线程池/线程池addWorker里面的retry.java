package com.example.demo.juc.线程池;

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
        /**
         * retry：可以理解为java中的一种特殊的标记，其中retry可以换成任何合法的命名。
         * a：，b： A13: .....都是可以的
         * retry：需要放在for，whlie，do...while的前面声明，变量只跟在break和continue后面。
         *
         * 如果retry没有在循环（for，while）里面，在执行到retry时，就会跳出整个循环。
         * 如果retry在循环里面，可以理解为跳到了关键字处执行，不管几层循环。continue理解也是一样。
         */
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
