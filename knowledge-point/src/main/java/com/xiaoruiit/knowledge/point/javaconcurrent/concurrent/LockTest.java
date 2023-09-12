package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发包14
 * @author hanxiaorui
 * @date 2023/9/6
 */
public class LockTest {

    public synchronized void funA(){
        System.out.println("funA");
        funB();
    }

    public synchronized void funB(){
        System.out.println("funB");
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lockInterruptibly();// 可中断锁

        lock.tryLock(1L, TimeUnit.SECONDS);// 支持超时的锁

        boolean b = lock.tryLock();// 非阻塞获取锁；尝试获取锁，获取不到就返回false，不会阻塞

        // 可重入锁案例
        LockTest lockTest = new LockTest();
        lockTest.funA();// funA调用funB时，因为锁可冲入，可以调用成功，不会产生死锁
    }
}
