package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

/**
 * @author hanxiaorui
 * @date 2023/10/24
 */
public final class STM {
    private STM() {}

    static final Object commitLock = new Object();// 提交数据用到的全局锁

    public static void atomic(TxnRunnable action) {
        boolean committed = false;
        while (!committed) {
            STMTxn txn = new STMTxn();
            action.run(txn);// 执行业务逻辑
            committed = txn.commit();
        }
    }
}
