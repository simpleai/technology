package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

/**
 * 支持事务的引用
 *
 * @author hanxiaorui
 * @date 2023/10/24
 */
public class TxnRef <T>{
    volatile VersionedRef curRef;// 当前数据，带版本号

    public TxnRef(T value) {
        this.curRef = new VersionedRef(value, 0L);
    }

    public T getValue(Txn txn){
        return txn.get(this);
    }

    // 在当前事务中设置数据
    public void setValue(T value, Txn txn) {
        txn.set(this, value);
    }
}
