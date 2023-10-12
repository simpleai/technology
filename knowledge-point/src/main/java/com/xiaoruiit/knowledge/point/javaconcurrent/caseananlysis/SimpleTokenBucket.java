package com.xiaoruiit.knowledge.point.javaconcurrent.caseananlysis;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

/**
 * 并发设计案例  38
 * 目的：请求限流
 * 原理：以固定速率往桶里放入令牌，请求时从桶里取出令牌，如果桶里没有令牌则拒绝请求（也可以实现为等待令牌产生）
 * 场景：
 *  1. 网络流量控制
 *  2. API接口限流
 *  3. 并发访问控制
 *  4. 消息队列流量控制
 *  5. 数据库访问控制
 *
 * 令牌桶算法更加灵活，可以根据令牌放入速率来控制请求的速率，通过桶容量对突发流量有一定的容忍度；
 * 漏桶算法以固定的速率处理请求，对突发流量有较好的控制。
 * @author hanxiaorui
 * @date 2023/10/11
 */
public class SimpleTokenBucket {
    private final int capacity; // 桶容量
    private int tokens; // 当前令牌数量
    private final long rate; // 令牌放入速率 单位：个/秒
    private long lastTime; // 上次放入令牌时间

    public SimpleTokenBucket(int capacity, long rate){
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = 0;
        this.lastTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(){
        refillTokens();// 刷新当前令牌数量
        if (tokens > 0){
            tokens--;
            return true;
        }

        return false;
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();

        if (now > lastTime){
            long newTokens = (now - lastTime) / 1000 * rate;
            if (newTokens > 0){
                tokens = Math.min(capacity, (int) (tokens + newTokens));
                lastTime = now;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleTokenBucket simpleTokenBucket = new SimpleTokenBucket(3, 1);

        for (int i = 0; i < 20; i++) {
            Thread.sleep(500);// 0.5秒请求一次
            System.out.println(simpleTokenBucket.tryAcquire());
        }

        RateLimiter rateLimiter = RateLimiter.create(1);// gugva的限流工具

    }
}
