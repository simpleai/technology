package com.xiaoruiit.knowledge.point.threadPool.entity;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "thread-pools")
public class ThreadPoolsProperties implements InitializingBean {
    private Map<String, ThreadPoolConfig> configs = new HashMap<>(); // 超过10个，启动时打印 error 日志

    @Override
    public void afterPropertiesSet() {
        configs.forEach((name, cfg) -> {
            if (cfg.getMaxPoolSize() == null) {
                cfg.setMaxPoolSize(cfg.getCorePoolSize() * 2);
            }
        });
    }
}
