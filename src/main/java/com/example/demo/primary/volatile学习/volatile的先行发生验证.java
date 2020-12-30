package com.example.demo.primary.volatile学习;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/29 4:02 下午
 * @Description:
 */
public class volatile的先行发生验证 {

    private int value = 0 ;

    public void setValue(int value){
        this.value = value ;
    }

    public int getValue(){
        return value ;
    }

    public static void main(String[] args) {
        volatile的先行发生验证 tmp = new volatile的先行发生验证();
        for (int i = 0; i < 10000; i++) {
            new Thread写(tmp).start();
            new Thread读(tmp).start();
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Thread写 extends Thread{
        private volatile的先行发生验证 tmp ;

        @Override
        public void run() {
            tmp.setValue(10);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Thread读 extends Thread{
        private volatile的先行发生验证 tmp ;

        @Override
        public void run() {
            int value = tmp.getValue();
            System.out.println(value);
        }
    }
}
