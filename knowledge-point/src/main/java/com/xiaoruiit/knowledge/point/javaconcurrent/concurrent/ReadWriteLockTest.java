package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 并发工具包17 实现简单缓存
 * @author hanxiaorui
 * @date 2023/9/7
 */
public class ReadWriteLockTest<K, V> {

    Map<K,V> cacheMap = new HashMap<>();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();

    private final Lock writeLock = readWriteLock.writeLock();

    public V get(K key){
        readLock.lock();
        try {
            return cacheMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public void put(K key, V value){
        writeLock.lock();
        try {
            cacheMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

}
