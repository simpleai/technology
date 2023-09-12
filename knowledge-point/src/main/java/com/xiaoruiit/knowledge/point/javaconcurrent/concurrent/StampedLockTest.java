package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.StampedLock;

/**
 * 并发工具包18
 *
 * 支持写锁，悲观读锁，乐观读
 *
 * 不支持重入，不支持条件变量
 *
 * readLock(); 不支持中断，调用中断CPU飙升。
 * stampedLock.readLockInterruptibly();支持中断
 *
 * @author hanxiaorui
 * @date 2023/9/7
 */
public class StampedLockTest {

    public static void main(String[] args) {
        final StampedLock stampedLock = new StampedLock();

        long stamp = stampedLock.readLock();

        try {
            // 读取数据
        } finally {
            stampedLock.unlockRead(stamp);// 释放读锁需传入stamp
        }

        long stamp2 = stampedLock.writeLock();

        try {
            // 写入数据
        } finally {
            stampedLock.unlockWrite(stamp2);// 释放写锁需传入stamp
        }
    }

}

/**
 * 乐观读案例
 */
class User {

    private String name;

    private int age;

    private final StampedLock stampedLock = new StampedLock();

    public void set(String name, int age){
        long stamp = stampedLock.writeLock();
        try {
            this.name = name;
            this.age = age;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    public String get(){
        long stamp = stampedLock.tryOptimisticRead();// 乐观读
        String name = this.name;
        int age = this.age;

        if (!stampedLock.validate(stamp)){// 读的过程存在写操作
            stamp = stampedLock.readLock();// 升级为悲观读锁. 不支持中断，调用中断CPU飙升，stampedLock.readLockInterruptibly();// 支持中断

            try {
                name = this.name;
                age = this.age;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return name + ":" + age;
    }
}
