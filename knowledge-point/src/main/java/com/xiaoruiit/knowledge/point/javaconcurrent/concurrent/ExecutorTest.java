package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发工具包22
 */
public class ExecutorTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,4,5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(16));

        threadPoolExecutor.setThreadFactory(new AllotThreadFactory("allot"));

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

    }

    /**
     * 为线程池中的线程设置名词前缀，方便jstack排查问题
     */
    static class AllotThreadFactory implements ThreadFactory{

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final AtomicInteger threadNumber  =new AtomicInteger(1);

        private String namePrefix;

        private final ThreadGroup group;

        public AllotThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix + "-" + poolNumber.getAndIncrement() + "-thread-";
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);

            return t;
        }
    }
}
