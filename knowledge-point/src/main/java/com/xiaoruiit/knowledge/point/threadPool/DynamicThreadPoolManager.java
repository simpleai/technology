package com.xiaoruiit.knowledge.point.threadPool;

import com.xiaoruiit.knowledge.point.threadPool.entity.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态线程池管理工具类，支持多线程池实例、动态参数调整、状态监控。
 */
@Slf4j
public class DynamicThreadPoolManager {

    private static final Map<String, ThreadPoolExecutor> POOL_MAP = new ConcurrentHashMap<>();
    private static Map<String, ThreadPoolConfig> lastConfigMap = new HashMap<>();

    /**
     * 获取线程池实例
     */
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = POOL_MAP.get(ThreadPoolConfig.DEFAULT_THREAD_POOL_NAME);
        if (threadPoolExecutor == null) {
            ThreadPoolConfig defaultConfig = new ThreadPoolConfig();
            defaultConfig.setMaxPoolSize(defaultConfig.getCorePoolSize() * 2);

            threadPoolExecutor = getOrCreatePool(
                    ThreadPoolConfig.DEFAULT_THREAD_POOL_NAME,
                    defaultConfig.getCorePoolSize(),
                    defaultConfig.getMaxPoolSize(),
                    defaultConfig.getQueueCapacity(),
                    defaultConfig.getKeepAliveTime(),
                    defaultConfig.getRejectedPolicy()
            );
        }
        return threadPoolExecutor;
    }

    /**
     * 获取线程池实例
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName) {
        ThreadPoolExecutor threadPoolExecutor = POOL_MAP.get(poolName);
        if (threadPoolExecutor == null) {
            log.error("线程池实例不存在：" + poolName);
        }
        return threadPoolExecutor;
    }

    /**
     * 创建或获取线程池实例
     */
    protected static ThreadPoolExecutor getOrCreatePool(String poolName, int corePoolSize, int maxPoolSize) {
        return POOL_MAP.computeIfAbsent(poolName, name -> {
            return getOrCreatePool(
                    name,
                    corePoolSize,
                    maxPoolSize,
                    0,
                    60L,
                    ThreadPoolConfig.RejectedPolicyEnum.CallerRunsPolicy
            );
        });
    }

    /**
     * 创建或获取线程池实例
     */
    protected static ThreadPoolExecutor getOrCreatePool(String poolName, int corePoolSize, int maxPoolSize, long keepAliveTime) {
        return POOL_MAP.computeIfAbsent(poolName, name -> {
            return getOrCreatePool(
                    name,
                    corePoolSize,
                    maxPoolSize,
                    0,
                    keepAliveTime,
                    ThreadPoolConfig.RejectedPolicyEnum.CallerRunsPolicy
            );
        });
    }

    /**
     * 创建或获取线程池实例
     */
    protected static ThreadPoolExecutor getOrCreatePool(String poolName, int corePoolSize, int maxPoolSize, int queueCapacity) {
        return POOL_MAP.computeIfAbsent(poolName, name -> {
            return getOrCreatePool(
                    name,
                    corePoolSize,
                    maxPoolSize,
                    queueCapacity,
                    60L,
                    ThreadPoolConfig.RejectedPolicyEnum.CallerRunsPolicy
            );
        });
    }

    /**
     * 创建或获取线程池实例
     * 默认拒绝策略为 CallerRunsPolicy 主线程执行。尽量保证数据一致性。主线程被关闭的情况下，任务被丢弃
     */
    protected static ThreadPoolExecutor getOrCreatePool(String poolName, int corePoolSize, int maxPoolSize, int queueCapacity, long keepAliveTime) {
        return POOL_MAP.computeIfAbsent(poolName, name -> {
            return getOrCreatePool(
                    name,
                    corePoolSize,
                    maxPoolSize,
                    queueCapacity,
                    keepAliveTime,
                    ThreadPoolConfig.RejectedPolicyEnum.CallerRunsPolicy
            );
        });
    }

    /**
     * 创建或获取线程池实例
     */
    protected static ThreadPoolExecutor getOrCreatePool(String poolName, int corePoolSize, int maxPoolSize, int queueCapacity, long keepAliveTime, ThreadPoolConfig.RejectedPolicyEnum policy) {
        return POOL_MAP.computeIfAbsent(poolName, name -> {

            ThreadPoolConfig config = new ThreadPoolConfig();
            config.setCorePoolSize(corePoolSize);
            config.setMaxPoolSize(maxPoolSize);
            config.setQueueCapacity(queueCapacity);
            config.setKeepAliveTime(keepAliveTime);
            config.setRejectedPolicy(policy);

            lastConfigMap.put(poolName, config.deepCopy(config));

             if (queueCapacity == 0){
                return new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        keepAliveTime, TimeUnit.SECONDS,
                        new SynchronousQueue<>(),
                        new NamedThreadFactory(name),
                        ThreadPoolConfig.parseRejectedPolicy(policy)
                );
            } else {
                return new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(queueCapacity),
                        new NamedThreadFactory(name),
                        ThreadPoolConfig.parseRejectedPolicy(policy)
                );
            }
        });
    }

    /**
     * 动态调整线程池参数
     *  todo
     *      队列动态调整。
     *      核心线程数、最大线程数从大改小.当前使用中的最大线程=16，修改为4，可能修改失败
     */
    public static void updatePoolParams(String poolName, Integer corePoolSize, Integer maxPoolSize, Integer queueCapacity, Long keepAliveTime, ThreadPoolConfig.RejectedPolicyEnum policy) {
        ThreadPoolExecutor executor = POOL_MAP.get(poolName);

        if (executor == null) return;
        executor.setCorePoolSize(corePoolSize);
        // 校验最大线程不能小于核心线程数
        if (maxPoolSize < corePoolSize) {
            throw new IllegalArgumentException("线程池配置错误：最大线程数不能小于核心线程数");
        }

        executor.setMaximumPoolSize(maxPoolSize);

        if (keepAliveTime != null) executor.setKeepAliveTime(keepAliveTime, TimeUnit.SECONDS);
        if (policy != null) executor.setRejectedExecutionHandler(ThreadPoolConfig.parseRejectedPolicy(policy));
        if (queueCapacity != null) {
            // 只能通过反射或重建队列，简单实现：不支持动态调整队列容量
            // 生产可用方案建议重建线程池
        }

        ThreadPoolConfig threadPoolConfig = lastConfigMap.get(poolName);
        threadPoolConfig.setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maxPoolSize)
                .setQueueCapacity(queueCapacity)
                .setKeepAliveTime(keepAliveTime)
                .setRejectedPolicy( policy);
        lastConfigMap.put(poolName, threadPoolConfig);
    }

    /**
     * 获取线程池配置
     */
    public static ThreadPoolConfig getThreadPoolConfig(String poolName) {
        return lastConfigMap.get(poolName);
    }

    /**
     * 获取线程池状态
     */
    public static ThreadPoolStatus getPoolStatus(String poolName) {
        ThreadPoolExecutor executor = POOL_MAP.get(poolName);
        if (executor == null) return null;
        return new ThreadPoolStatus(
                executor.getCorePoolSize(),
                executor.getMaximumPoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                executor.getTaskCount(),
                executor.getCompletedTaskCount(),
                executor.getKeepAliveTime(TimeUnit.SECONDS),
                executor.getRejectedExecutionHandler().getClass().getSimpleName()
        );
    }

    /**
     * 获取所有线程池状态
     */
    public static Map<String, ThreadPoolStatus> getAllPoolStatus() {
        Map<String, ThreadPoolStatus> statusMap = new ConcurrentHashMap<>();
        for (String poolName : POOL_MAP.keySet()) {
            statusMap.put(poolName, getPoolStatus(poolName));
        }
        return statusMap;
    }

    /**
     * 线程池状态信息
     */
    public static class ThreadPoolStatus {
        public final int corePoolSize;
        public final int maxPoolSize;
        public final int activeCount;
        public final int queueSize;
        public final long taskCount;
        public final long completedTaskCount;
        public final long keepAliveTimeSeconds;
        public final String rejectedHandler;
        public ThreadPoolStatus(int corePoolSize, int maxPoolSize, int activeCount, int queueSize, long taskCount, long completedTaskCount, long keepAliveTimeSeconds, String rejectedHandler) {
            this.corePoolSize = corePoolSize;
            this.maxPoolSize = maxPoolSize;
            this.activeCount = activeCount;
            this.queueSize = queueSize;
            this.taskCount = taskCount;
            this.completedTaskCount = completedTaskCount;
            this.keepAliveTimeSeconds = keepAliveTimeSeconds;
            this.rejectedHandler = rejectedHandler;
        }
    }

    /**
     * 命名线程工厂
     */
    public static class NamedThreadFactory implements ThreadFactory {
        private final String poolName;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        public NamedThreadFactory(String poolName) {
            this.poolName = poolName;
        }
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, poolName + "-thread-" + threadNumber.getAndIncrement());
        }
    }
}
