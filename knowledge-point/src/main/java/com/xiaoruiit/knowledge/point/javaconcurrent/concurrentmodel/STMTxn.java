package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 软件事务内存模型
 * 事务实现类
 * @author hanxiaorui
 * @date 2023/10/24
 */
public final class STMTxn implements Txn{

    private static AtomicLong txnSeq = new AtomicLong(0L);

    private Map<TxnRef, VersionedRef> intxnMap = new HashMap<>();// 所有事务相关数据

    private Map<TxnRef, VersionedRef> writeTxnMap = new HashMap<>();// 所有事务需要修改的数据

    private long txnId;// 当前事务id

    STMTxn() {
        txnId = txnSeq.incrementAndGet();// 自动生成当前事务ID
    }

    /**
     * 获取当前事务中的数据
     * @param ref
     * @return
     * @param <T>
     */
    @Override
    public <T> T get(TxnRef<T> ref) {
        if(!intxnMap.containsKey(ref)){
            intxnMap.put(ref, ref.curRef);
        }
        return (T) intxnMap.get(ref).value;
    }

    /**
     * 在当前事务中修改数据
     * @param ref
     * @param value
     * @param <T>
     */
    @Override
    public <T> void set(TxnRef<T> ref, T value) {
        if(!intxnMap.containsKey(ref)){
            intxnMap.put(ref, ref.curRef);
        }
        writeTxnMap.put(ref, new VersionedRef(value, txnId));
    }

    boolean commit(){
        synchronized (STM.commitLock){
            boolean isValid = true;
            // 校验所有读过的数据是否发生过变化
            for (Map.Entry<TxnRef, VersionedRef> entry : intxnMap.entrySet()) {
                VersionedRef curRef = entry.getKey().curRef;
                VersionedRef readRef = entry.getValue();
                if(curRef.version != readRef.version){// 通过版本号是否一致来验证
                    isValid = false;
                    break;
                }
            }
            // 如果校验通过，则所有更改生效
            if(isValid){
                writeTxnMap.forEach((k,v)->{
                    k.curRef = v;
                });
            }
            return isValid;
        }
    }
}
