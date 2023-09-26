//package com.xiaoruiit.knowledge.point.javaconcurrent.designpattern;
//
//import java.lang.ref.WeakReference;
//
//public class ThreadLocalTest {
//    java.lang.ThreadLocal
//}
//
//class Thread {
//    // 内部持有 ThreadLocalMap
//    ThreadLocal.ThreadLocalMap threadLocals;
//}
//
//class ThreadLocal<T> {
//    public T get() {
//// 首先获取线程持有的
////ThreadLocalMap
//        ThreadLocalMap map = Thread.currentThread().threadLocals;
//// 在 ThreadLocalMap 中查找变量
//        Entry e = map.getEntry(this);
//        return e.value;
//    }
//
//    static class ThreadLocalMap {
//        // 内部是数组而不是 Map
//        Entry[] table;
//
//        // 根据 ThreadLocal 查找 Entry
//        Entry getEntry(ThreadLocal key) {
//            // 省略查找逻辑
//            return new Entry(key);
//        }
//
//        //Entry 定义
//        static class Entry extends WeakReference<ThreadLocal> {
//            Object value;
//
//            public Entry(ThreadLocal referent) {
//                super(referent);
//            }
//        }
//    }
//}
