package com.xiaoruiit.knowledge.point.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RedissonLockExample {

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        List<String> itemIds = Arrays.asList("item1", "item2", "item3");

        Map<String, Integer> quantities = new HashMap<>(); // 物料扣减数量列表
        for (String itemId : itemIds) {
            quantities.put(itemId, 10);
        }

        // 获取物料锁
        RLock[] locks = new RLock[itemIds.size()];
        for (int i = 0; i < itemIds.size(); i++) {
            String lockKey = "lock:" + itemIds.get(i);
            RLock lock = redisson.getLock(lockKey);
            locks[i] = lock;
        }

        RedissonMultiLock multiLock = new RedissonMultiLock(locks);
        multiLock.lock();

        try {
            // 在加锁的范围内执行扣减操作
            for (String itemId : itemIds) {
                String key = "inventory:" + itemId;
                RAtomicLong atomicLong = redisson.getAtomicLong(key);
                int quantityToSubtract = quantities.get(itemId);
                atomicLong.getAndAdd(-quantityToSubtract);
                log.warn(quantityToSubtract + "");
            }
        } finally {
            // 释放物料锁
            multiLock.unlock();
        }
    }
}


