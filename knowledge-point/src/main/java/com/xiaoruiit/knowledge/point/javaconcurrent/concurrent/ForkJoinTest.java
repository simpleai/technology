package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 并发工具包26 任务分治
 * @author hanxiaorui
 * @date 2023/9/22
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(   );

        ForkJoinTask<Integer> forkJoinTask = new Fibonacci(7) {};

        Integer invoke = forkJoinPool.invoke(forkJoinTask);

        System.out.println(invoke);

        System.out.println(fobinacci(7));
    }

    public static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1){// 结束条件
                return n;
            }

            Fibonacci f1 = new Fibonacci(n - 1);

            // 创建子任务
            f1.fork();

            Fibonacci f2 = new Fibonacci(n - 2);

            return f2.compute() + f1.join();
        }
    }

    public static int fobinacci(int n){
        if (n <= 1){
            return n;
        }
        return fobinacci(n - 1) + fobinacci(n - 2);
    }
}
