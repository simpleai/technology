package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具包24
 */
public class CompleableFutureTest {

    public static void main(String[] args){
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("洗水壶");

            sleep(1);

            System.out.println("烧水");

            sleep(15);
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {// 支持返回值
            System.out.println("洗茶壶");

            sleep(1);

            System.out.println("洗茶杯");

            sleep(2);

            System.out.println("拿茶叶");

            sleep(1);

            return "龙井";
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            System.out.println("泡茶...");

            return "上茶：" + tf;
        });

        System.out.println(f3.join());

        // 串行
        /*f1.thenApply(); // 支持参数和返回值
        f1.thenAccept(); // 支持参数，不支持返回值
        f1.thenRun();// 不支持参数，也不支持返回值
        f1.thenCompose(); // 除了新建一个子流程，最终结果和thenApply相同*/

    }

    private static void sleep(int millis) {
        try {
            TimeUnit.SECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
