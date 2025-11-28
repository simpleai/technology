package com.xiaoruiit.knowledge.point.threadPool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ThreadPoolConfig {

    public static final String DEFAULT_THREAD_POOL_NAME = "defaultPool";

    private Integer corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private Integer maxPoolSize; // 会初始化为 corePoolSize * 2
    private Integer queueCapacity = 0;
    /**
     * 单位秒
     */
    private Long keepAliveTime = 60L;
    private RejectedPolicyEnum rejectedPolicy = RejectedPolicyEnum.CallerRunsPolicy;
    private Boolean haveLarkNotify = true;
    private String larkNotifyUrl;

    public enum RejectedPolicyEnum {
        AbortPolicy,
        CallerRunsPolicy,
        DiscardPolicy,
        DiscardOldestPolicy;

        public static RejectedPolicyEnum getRejectedPolicy(String rejectedPolicy){
            for (RejectedPolicyEnum rejectedPolicyEnum : RejectedPolicyEnum.values()) {
                if (rejectedPolicyEnum.name().equals(rejectedPolicy)) {
                    return rejectedPolicyEnum;
                }
            }
            throw new IllegalArgumentException("rejectedPolicy is not valid");
        }
    }

    public static RejectedExecutionHandler parseRejectedPolicy(RejectedPolicyEnum policy) {
        if (policy == null) return new ThreadPoolExecutor.AbortPolicy();
        switch (policy) {
            case CallerRunsPolicy:
                return new ThreadPoolExecutor.CallerRunsPolicy();
            case DiscardPolicy:
                return new ThreadPoolExecutor.DiscardPolicy();
            case DiscardOldestPolicy:
                return new ThreadPoolExecutor.DiscardOldestPolicy();
            case AbortPolicy:
            default:
                return new ThreadPoolExecutor.AbortPolicy();
        }
    }

    public static ThreadPoolConfig deepCopy(ThreadPoolConfig config) {
        if (config == null) return null;
        ThreadPoolConfig copy = new ThreadPoolConfig();
        copy.setCorePoolSize(config.getCorePoolSize());
        copy.setMaxPoolSize(config.getMaxPoolSize());
        copy.setQueueCapacity(config.getQueueCapacity());
        copy.setKeepAliveTime(config.getKeepAliveTime());
        copy.setRejectedPolicy(config.getRejectedPolicy());
        return copy;
    }
}