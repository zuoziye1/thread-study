package com.example.demo.primary.volatile学习;

/**
 * 我们这里来分析下为什么双重锁校验会需要volatile
 */
public class 单例模式的应用_双重锁校验 {

    private static volatile 单例模式的应用_双重锁校验 single ;

    private 单例模式的应用_双重锁校验(){}

    public static 单例模式的应用_双重锁校验 getSingle(){
        if(single == null){
            synchronized (单例模式的应用_双重锁校验.class){
                if (single == null){
                    single = new 单例模式的应用_双重锁校验();
                }
            }
        }
        return single;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                单例模式的应用_双重锁校验 single = 单例模式的应用_双重锁校验.getSingle();
                System.out.println(single.hashCode());
            }).start();
        }

    }
}
