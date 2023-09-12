package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 并发工具包16
 * @author hanxiaorui
 * @date 2023/9/6
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);// 1表示只有一个许可证

        new Thread(() -> {
            try {
                semaphore.acquire();// 获取许可证,计数器减1，如果计数器的值小于0，线程会被阻塞
                System.out.println("线程1获取到许可证");
                System.out.println(semaphore.toString());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();// 释放许可证，计数器加1，如果计数器的值小于等于0，会唤醒一个等待的线程
            }
        }).start();

        new Thread(() -> {
            try {
                semaphore.acquire();// 获取许可证
                System.out.println("线程2获取到许可证");
                System.out.println(semaphore.toString());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();// 释放许可证
            }
        }).start();
    }
}
