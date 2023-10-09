package com.xiaoruiit.knowledge.point.javaconcurrent.designpattern;

import java.lang.ref.WeakReference;

/**
 * 并发设计模式 30 ThreadLocal结构
 */
public class ThreadLocalTest {
}

/**
 * ThreadLocal使用
 */
final class UserContextHolder {

    private static final java.lang.ThreadLocal<UserContext> THREAD_LOCAL = new java.lang.ThreadLocal();// 不同线程哈希表(ThreadLocalMap)中的key（ThreadLocal引用）相同，但不同线程的哈希表都是各自自己的，是不同的

    private UserContextHolder() {
    }

    public static UserContext current() {
        return THREAD_LOCAL.get();
    }

    public static void clean() {
        THREAD_LOCAL.remove();
        THREAD_LOCAL.set(null);
    }

    public static void set(UserContext userContext) {
        THREAD_LOCAL.set(userContext);
    }

    static class UserContext{
        String userName;
    }
}

class MyThread {
    // 内部持有 ThreadLocalMap
    ThreadLocal.ThreadLocalMap threadLocals = null;

    public static MyThread currentThread() {
        MyThread thread = new MyThread();
        thread.threadLocals = new ThreadLocal.ThreadLocalMap();
        return thread;
    }
}

class ThreadLocal<T> {
    public T get() {
        // 首先获取线程持有的ThreadLocalMap
        ThreadLocalMap map = MyThread.currentThread().threadLocals;
        // 在 ThreadLocalMap 中查找变量
        ThreadLocalMap.Entry e = map.getEntry(this);
        return (T) e.value;
    }

    public void set(T value) {
        MyThread t = MyThread.currentThread();
        ThreadLocal.ThreadLocalMap map = getMap(t);
        if (map != null){
//            map.set(this, value);// 不同线程哈希表中的key（ThreadLocal引用）相同，但不同线程的哈希表都是各自自己的，是不同的
        }

        else
            createMap(t, value);
    }

    void createMap(MyThread t, T firstValue) {
        t.threadLocals = new ThreadLocal.ThreadLocalMap(this, firstValue);
    }

    ThreadLocal.ThreadLocalMap getMap(MyThread t) {
        return t.threadLocals;
    }

    static class ThreadLocalMap {
        // 内部是数组而不是 Map
        Entry[] table;

        // 根据 ThreadLocal 查找 Entry
        Entry getEntry(ThreadLocal key) {
            // 省略查找逻辑
            return new Entry(key);
        }

        // Entry 定义
        static class Entry extends WeakReference<ThreadLocal> {
            Object value;

            public Entry(ThreadLocal referent) {
                super(referent);
            }
        }

        ThreadLocalMap(){}

        ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
//            table = new ThreadLocal.ThreadLocalMap.Entry[INITIAL_CAPACITY];
//            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
//            table[i] = new ThreadLocal.ThreadLocalMap.Entry(firstKey, firstValue);
//            size = 1;
//            setThreshold(INITIAL_CAPACITY);
        }
    }
}


