package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发工具包25
 * 生产者、消费者解耦
 * 用途：
 *  1. 一批相同返回值的任务，在另一处使用返回结果。但是不关心不同任务返回结果的顺序。相同任务的返回结果顺序与提交任务的顺序一致。
 *  2. 使用任务集的第一个非空结果，忽略任何遇到异常的任务，并在第一个任务准备好时取消所有其他任务。
 * @author hanxiaorui
 * @date 2023/9/22
 */
public class CompletionServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletionService<Integer> cs = new ExecutorCompletionService<>(executorService);// 默认将结果Future对象存入到无界阻塞队列中

        cs.submit(() -> getPriceByS1());

        cs.submit(() -> getPriceByS2());

        cs.submit(() -> getPriceByS3());

        for (int i = 0; i < 3; i++) {
            Integer i1 = cs.take().get();// take方法获取并移出第一个元素。未获取到时阻塞，直到有结果返回
            executorService.execute(() -> save(i1));
        }

        System.out.println("主线程未被阻塞over");
    }

    private static void save(Integer i1) {
        System.out.println("save:" + i1);
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
