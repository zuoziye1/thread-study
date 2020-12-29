# synchronized 工作演示
Synchronized示例 类

# 同步的实现原理
参考：https://blog.csdn.net/javazejian/article/details/72828483#%E7%90%86%E8%A7%A3java%E5%AF%B9%E8%B1%A1%E5%A4%B4%E4%B8%8Emonitor

## 先生成字节码指令
javap -c SynchronizedDemo.java 
javap -c SynchronizedDemo.class > SynchronizedDemo指令.txt

## 分析
https://editor.csdn.net/md/?articleId=111665598

# 为什么wait和notify要在synchronized中使用？
调用obj的wait(), notify()方法前，必须获得obj锁，也就是必须写在synchronized(obj) 代码段内。
因为获取对象的monitor只能通过synchronized。而wait和notify分别是对锁（monitor）的释放，和提醒要锁的线程可以抢锁了。