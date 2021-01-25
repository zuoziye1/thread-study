package com.example.demo.juc.atomic.cas;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/20 3:35 下午
 * @Description:
 */
public class 原子引用 {

    public static void main(String[] args){
        User user = new User(20,"张三");
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(user);
        System.out.println(atomicReference.get());
    }

    @Data
    @AllArgsConstructor
    private static class User {
        int age;
        String name;
    }
}
