package com.xiaoruiit.knowledge.point.javaconcurrent.designpattern;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发设计模式35
 * 终止线程
 * @author hanxiaorui
 * @date 2023/10/9
 */
public class StopThread {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.start();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        proxy.stop();


        Proxy2 proxy2 = new Proxy2();
        proxy2.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        proxy2.stop();


        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 终止线程池中的线程，返回未执行的任务
        List<Runnable> runnables = executorService.shutdownNow();
    }
}

class Proxy {
    boolean started = false;

    Thread rptThread;

    synchronized void start() {
        if (started) {// 保证线程只启动一次
            return;
        }
        started = true;
        rptThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // 采集系统数据，存储
                System.out.println("Proxy采集系统数据，存储");
                // 每隔两秒采集
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // 重新设置线程中断状态
                    Thread.currentThread().interrupt();
                }
            }
            // 执行到此处说明线程马上终止
            started = false;
        });
        rptThread.start();
    }

    synchronized void stop() {
        rptThread.interrupt();
    }
}

class Proxy2{
    boolean started = false;

    volatile boolean stop = false;

    Thread rptThread;

    synchronized void start() {
        if (started) {// 保证线程只启动一次
            return;
        }
        started = true;
        rptThread = new Thread(() -> {
            while (!stop) {
                // 采集系统数据，存储
                System.out.println("Proxy2采集系统数据，存储");
                // 每隔两秒采集
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("Proxy2线程被中断");
                    // 重新设置线程中断状态
                    Thread.currentThread().interrupt();
                }
            }
            // 执行到此处说明线程马上终止
            started = false;
        });
        rptThread.start();
    }

    synchronized void stop() {
        stop = true;
        rptThread.interrupt();// 如果线程休眠，可唤醒线程
    }
}
