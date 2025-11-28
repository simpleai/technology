package com.xiaoruiit.knowledge.point.threadPool;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.xiaoruiit.knowledge.point.threadPool.entity.ThreadPoolConfig;
import com.xiaoruiit.knowledge.point.threadPool.entity.ThreadPoolsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Slf4j
@Component
public class ThreadPoolDynamicRefresher {
    private final ThreadPoolsProperties threadPoolsProperties;

    @Autowired
    private ThreadPoolNotify threadPoolNotify;

    public ThreadPoolDynamicRefresher(ThreadPoolsProperties threadPoolsProperties) {
        this.threadPoolsProperties = threadPoolsProperties;
    }

    @PostConstruct
    public void init() {
        // 初始化所有线程池
        Map<String, ThreadPoolConfig> configs = threadPoolsProperties.getConfigs();

        if (configs != null) {
            if (configs.size() > 10) {
                log.error("[ThreadPoolDynamicRefresher] 线程池数量过多，请检查配置文件");
            }

            for (Map.Entry<String, ThreadPoolConfig> stringThreadPoolConfigEntry : configs.entrySet()) {
                DynamicThreadPoolManager.getOrCreatePool(
                        stringThreadPoolConfigEntry.getKey(),
                        stringThreadPoolConfigEntry.getValue().getCorePoolSize(),
                        stringThreadPoolConfigEntry.getValue().getMaxPoolSize(),
                        stringThreadPoolConfigEntry.getValue().getQueueCapacity(),
                        stringThreadPoolConfigEntry.getValue().getKeepAliveTime(),
                        stringThreadPoolConfigEntry.getValue().getRejectedPolicy()
                );
            }
            log.info("[ThreadPoolDynamicRefresher] 线程池初始化完成");
        }
    }

    /**
     * 监听配置文件刷新事件
     * 支持修改已有线程池，不支持动态新增一个线程池
     */
    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh() {
        Map<String, ThreadPoolConfig> newConfigs = threadPoolsProperties.getConfigs();
        if (newConfigs == null) return;
        for (Map.Entry<String, ThreadPoolConfig> stringThreadPoolConfigEntry : newConfigs.entrySet()) {
            String name = stringThreadPoolConfigEntry.getKey();
            ThreadPoolConfig newConfig = stringThreadPoolConfigEntry.getValue();
            ThreadPoolConfig oldConfig = DynamicThreadPoolManager.getThreadPoolConfig(name);
            if (oldConfig == null) {
                log.info("[ThreadPoolDynamicRefresher] 线程池:{} 不存在，重新启动服务器后新建", name);
                continue;
            }
            if (!newConfig.equals(oldConfig)) {
                // 有变动，刷新线程池参数
                log.info("[ThreadPoolDynamicRefresher] 线程池:{} 参数有变动，开始刷新", name);
                try {
                    DynamicThreadPoolManager.updatePoolParams(
                            name,
                            newConfig.getCorePoolSize(),
                            newConfig.getMaxPoolSize(),
                            newConfig.getQueueCapacity(),
                            newConfig.getKeepAliveTime() == null ? null : newConfig.getKeepAliveTime().longValue(),
                            newConfig.getRejectedPolicy()
                    );
                    threadPoolNotify.updateNotify(name, newConfig, true);
                    log.info("[ThreadPoolDynamicRefresher] 线程池:{} 参数:{} 参数刷新成功", name, JSON.toJSONString(newConfig, JSONWriter.Feature.WriteEnumsUsingName));
                } catch (Exception e){
                    log.error("[ThreadPoolDynamicRefresher] 线程池:{} 参数刷新失败", name, e);
                    threadPoolNotify.updateNotify(name, newConfig, false);
                }
            }
        }
    }

}