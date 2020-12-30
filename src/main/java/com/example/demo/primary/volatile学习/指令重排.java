package com.example.demo.primary.volatile学习;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 姚飞虎
 * @Date: 2020/12/29 2:58 下午
 * @Description:
 */
public class 指令重排 {

//    Map configOptions;
//    char[] configText;
//    //此变量必须定义为
//    volatile boolean initialized=false;
//
//    Thread tmp = new Thread(()->{
//
//        //假设以下代码在线程A中执行
//        // 模拟读取配置信息，当读取完成后将initialized设置为true以通知其他线程配置可用
//        configOptions=new HashMap();
//        configText = readConfigFile();
//        processConfigOptions(configText,configOptions);
//        initialized=true;
//        //假设以下代码在线程B中执行
//        // 等待initialized为true，代表线程A已经把配置信息初始化完成
//        while(!initialized){ sleep(); }
//        //使用线程A中初始化好的配置信息
//        doSomethingWithConfig();
//    });
//
//    /**
//     * 模拟读取配置文件
//     */
//    public char[] readConfigFile(){
//        char[] configText = new char[2];
//        configText[0] = 'a';
//        configText[0] = 'b';
//    }

}
