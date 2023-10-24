package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

/**
 * @author hanxiaorui
 * @date 2023/10/24
 */
@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}
