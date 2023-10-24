package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

/**
 * @author hanxiaorui
 * @date 2023/10/24
 */
public interface Txn {
    <T> T get(TxnRef<T> ref);

    <T> void set(TxnRef<T> ref, T value);
}
