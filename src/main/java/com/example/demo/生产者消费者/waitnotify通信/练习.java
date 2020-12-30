package com.example.demo.生产者消费者.waitnotify通信;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/29 11:57 上午
 * @Description:
 */
public class 练习 {

    private static List<String> 仓库 = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Producer extends Thread{

        private List<String> 仓库 ;

        @Override
        public void run() {
            synchronized (仓库){
                仓库.add("生产一件产品");
                System.out.println("生产了一件产品----->");
                仓库.notifyAll();
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Consumer extends Thread{
        private List<String> 仓库 ;

        @Override
        public void run() {
            synchronized (仓库){
                if(CollectionUtils.isEmpty(仓库)){
                    try {
                        仓库.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("仓库是空的----->");
                }
                仓库.remove(0);
                System.out.println("消费了一件产品----->");
            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer1 = new Consumer(仓库);
        Consumer consumer2 = new Consumer(仓库);
        Producer producer = new Producer(仓库);

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}
