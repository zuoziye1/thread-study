package com.example.demo.primary.sync;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/25 3:41 下午
 * @Description:
 */
public class 同步方法 {

    /**
     * 公共资源
     */
    public int i;

    /**
     * 同步方法
     */
    public synchronized void syncTask(){
        i++;
    }
}
