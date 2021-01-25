package com.example.demo.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLH锁 implements Lock {
    private final ThreadLocal<Node> preNode = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);
    private final AtomicReference<Node> tail = new AtomicReference<>(new Node());

    private static class Node {
        private volatile boolean locked;
    }

    @Override
    public void lock() {
        final Node node = this.node.get();
        node.locked = true;
        Node pre = this.tail.getAndSet(node);
        this.preNode.set(pre);
        while (pre.locked) ;
    }

    @Override
    public void unlock() {
        final Node node = this.node.get();
        node.locked = false;
        this.node.set(this.preNode.get());
    }














    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
