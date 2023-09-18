package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具包 23
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(16);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 5L, TimeUnit.MINUTES, arrayBlockingQueue);

        System.out.println("洗水壶1分钟");
        Future<String> submit = threadPoolExecutor.submit(() -> {
            System.out.println("洗茶壶");
            System.out.println("拿茶叶");

            return "龙井";
        });

        System.out.println("烧水壶");

        String string = submit.get();

        System.out.println("泡茶");

        System.out.println("上茶：" + string);
    }
}
