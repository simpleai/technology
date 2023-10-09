package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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


        // 输出三个报价最低值，非只有异步，使用CompletableFuture
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> i1 = CompletableFuture.supplyAsync(() -> getPriceByS1(), executorService);
        CompletableFuture<Integer> i2 = CompletableFuture.supplyAsync(() -> getPriceByS2(), executorService);
        CompletableFuture<Integer> i3 = CompletableFuture.supplyAsync(() -> getPriceByS3(), executorService);
        CompletableFuture.allOf(i1, i2, i3).thenRun(() -> {
            try {
                System.out.println("result:" + i1.get());
                System.out.println("result:" + i2.get());
                System.out.println("result:" + i3.get());
                Integer min = Integer.MAX_VALUE;
                for (int i = 0; i < 3; i++) {
                    min = Integer.min(min,i1.get());
                }
                System.out.println("min:" + min);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void sleep(int millis) {
        try {
            TimeUnit.SECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer getPriceByS3() {
        return 3;
    }

    private static Integer getPriceByS2() {
        return 2;
    }

    private static Integer getPriceByS1() {
        return 1;
    }
}
