package com.example.demo.primary.threadLocal学习;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/19 3:14 下午
 * @Description:
 */
public class 简单使用 {

    private static ThreadLocal<User> userThreadLocal = ThreadLocal.withInitial(
            () -> {
                User user = new User();
                user.setName("yaofeihu");
                user.setAge(18);
                return user;
            }
    );

    public static void main(String[] args) {
        new Thread(
                ()->{
                    /**
                     * 每个线程拿到的都是自己的本地副本
                     */
                    User user = userThreadLocal.get();
                    user.setAge(19);
                    System.out.println(user.hashCode());
                    System.out.println(user);
                }
        ).start();

        new Thread(
                ()->{
                    User user = userThreadLocal.get();
                    System.out.println(user.hashCode());
                    System.out.println(user);
                }
        ).start();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class User {

        private String name;

        private int age;
    }
}
