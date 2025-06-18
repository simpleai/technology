package com.xiaoruiit.knowledge.point.preventingDuplicates.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RequestIdStore implements ApplicationContextAware {// 实现 ApplicationContextAware 接口，用于：在类加载时，Spring 会自动注入 ApplicationContext，以便动态读取配置。
    private ApplicationContext applicationContext;
    private static final String PREFIX = "once:request:";
    private static final Lock lock = new ReentrantLock();
    private static volatile JedisPool jedisPool;

    public RequestIdStore() {
    }

    public boolean acquireLock(String requestId, long ttlSeconds) {
        Jedis jedis = this.getJedisPool().getResource();

        boolean var6;
        try {
            // nx: 仅当key不存在时设置
            String result = jedis.set(PREFIX + requestId, "1", SetParams.setParams().nx().ex((int)ttlSeconds));
            var6 = "OK".equals(result);
        } catch (Throwable var8) {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Throwable var7) {
                    var8.addSuppressed(var7);
                }
            }

            throw var8;
        }

        if (jedis != null) {
            jedis.close();
        }

        return var6;
    }

    public void releaseLock(String requestId) {
        Jedis jedis = this.getJedisPool().getResource();

        try {
            jedis.del(PREFIX + requestId);
        } catch (Throwable var6) {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }
            }

            throw var6;
        }

        if (jedis != null) {
            jedis.close();
        }

    }

    /**
     * 懒加载 + 双重检查锁定（DCL）模式初始化 Jedis 连接池
     * 在高并发场景下，频繁建立和销毁连接会成为性能瓶颈。
     * @return
     */
    private JedisPool getJedisPool() {
        if (jedisPool == null) {
            lock.lock();// 线程安全，使用 ReentrantLock。

            try {
                if (jedisPool == null) {// todo验证
                    // Redis 连接参数从 applicationContext 环境中读取，如：
                    // redis:
                    //  pool:
                    //    host: localhost
                    //    port: 6379

                    /*
                        这样获取可以不实现 ApplicationContextAware 接口.
                        @Value("${redis.pool.host}")
                        private String host;

                        @Value("${redis.pool.port}")
                        private Integer port;
                     */
                    String host = this.applicationContext.getEnvironment().getProperty("redis.pool.host");// 需要实现 ApplicationContextAware 接口.
                    Integer port = Integer.valueOf(this.applicationContext.getEnvironment().getProperty("redis.pool.port"));
                    // 配置并创建一个 Redis 连接池，用于高效管理和复用 Redis 连接，避免频繁创建和销毁连接的性能开销。
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(100);// 最大连接数.设置为略高于实际并发数（如 200）。
                    config.setMaxIdle(20);// 最大空闲连接数.设置为实际高峰期后空闲连接的平均数量。例如：系统高峰期，有 60 个线程并发使用 Redis；但不是一直都 60 个，有时高峰结束后瞬间会有 20~30 个连接变空闲. 那就设置为 30 个。能避免频繁创建销毁连接，也不浪费太多资源。
                    config.setMinIdle(5);// 最小空闲连接数.确保预热连接数，减少首次连接延迟。
                    config.setBlockWhenExhausted(true);// 连接耗尽时是否阻塞，默认为true，如果为false，则jedis.getResource()会抛出异常。
                    jedisPool = new JedisPool(config, host, port);
                }
            } finally {
                lock.unlock();
            }
        }

        return jedisPool;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
