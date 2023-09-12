package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * 并发工具包21
 * @author hanxiaorui
 * @date 2023/9/12
 */
public class AtomicClassTest {

    public static void main(String[] args) {
        // 基本数据类型
        AtomicLong atomicLong = new AtomicLong();
        atomicLong.getAndIncrement();
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicBoolean atomicBoolean = new AtomicBoolean();

        // 数组
        AtomicLongArray atomicLongArray = new AtomicLongArray(10);
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        AtomicReferenceArray<String> atomicReferenceArray = new AtomicReferenceArray<>(10);

        // 累加器 如果仅仅需要累加操作，使用原子化的累加器性能会更好。
        DoubleAccumulator doubleAccumulator = new DoubleAccumulator((x, y) -> x + y, 0);
        DoubleAdder doubleAdder = new DoubleAdder();
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
        LongAdder longAdder = new LongAdder();

        // 引用类型
        AtomicReference<String> atomicReference = new AtomicReference<>();
        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("", 0);
        AtomicMarkableReference<String> atomicMarkableReference = new AtomicMarkableReference<>("", false);

        //对象属性更新器
        AtomicIntegerFieldUpdater<User> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        AtomicLongFieldUpdater<User> atomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(User.class, "id");
        AtomicReferenceFieldUpdater<User, String> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");
    }
}
