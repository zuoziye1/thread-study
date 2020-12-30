package com.example.demo.primary;

import java.util.Vector;

/**
 * 我们通过vector来演示即便各个方法都用了synchronized,但是依然不是绝对线程安全的，
 * 绝对线程安全往往做不到，或者说是需要付出很大的代价。
 *
 * @Author: 姚飞虎
 * @Date: 2020/12/29 5:35 下午
 * @Description:
 */
public class 绝对线程安全 {

    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {


        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }
            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });
            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 虽然vector是线程安全的，但是也不是说是绝对安全的，绝对安全是说，不管怎么运行代码，都是安全的。
                     * 这在很多时候需要付出过大的代价，而且可能也是不符合实际的。
                     *
                     * 我们来看vector，虽然线程安全，各方法都用synchronized进行了同步，但是依然会存在一种情况：
                     * removeThread 在for (int i = 0; i < vector.size(); i++) 时，由于调用了vector.size()，
                     * 假设此时的 size = 100
                     * 需要获取monitor，它正常的获取并执行，此时就释放了，然后没有立马执行 vector.remove(i)
                     * 可能出现 printThread 在 for (int i = 0; i < vector.size(); i++)
                     * 由于vector.size()要获取monitor，立马抢到，而且cpu时间片也刚好抢到，就会出现它也正常的
                     * 执行并释放了锁,此时size = 100，之后vector.remove(i) 抢到monitor并执行，此时size = 99
                     * 然后 System.out.println((vector.get(i)))
                     * 抢到monitor并执行，但是问题是，在这个 printThread 线程的
                     * for (int i = 0; i < vector.size(); i++)  处，已经判断过状态了，假设刚才刚好判断到 i = 99 < size = 100
                     * 也就是说此时i = 99 ，但是呢，基于remove后，size = 99 ,i = 99 就数组越界了。
                     *
                     * 而想要这段代码线程安全，就要在判断 size 和 remove 是原子的。
                     * 判断size,并get 是原子的。加上synchronized就可以了。但是这就又增大了同步的范围
                     */
                    for (int i = 0; i < vector.size(); i++) {
                        try {
                            System.out.println((vector.get(i)));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("--------->出错啦");
                        }
                    }
                }
            });
            removeThread.start();
            printThread.start();
            //不要同时产生过多的线程，否则会导致操作系统假死
            while (Thread.activeCount() > 20) ;
        }
    }

    private static class PrintThread extends Thread{

    }
}
