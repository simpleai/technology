package com.xiaoruiit.knowledge.point.javaconcurrent.concurrentmodel;

/**
 * @author hanxiaorui
 * @date 2023/10/24
 */
public class MainAccountHandle {
}

class Account {
    // 余额
    private TxnRef<Integer> balance;
    // 构造方法
    public Account(int balance) {
        this.balance = new TxnRef<Integer>(balance);
    }
    // 转账操作
    public void transfer(Account target, int amt){
        STM.atomic((txn)->{// atomic方法将转账操作的原子性包在一个方法中
            Integer from = balance.getValue(txn);// 获取当前事务中的余额值。事务会将查询时的版本号记录下来，执行完成时自动对比版本号
            balance.setValue(from-amt, txn);// 设置当前事务中的余额值。事务会将修改时的版本号记录下来，执行完成时自动对比版本号
            Integer to = target.balance.getValue(txn);
            target.balance.setValue(to+amt, txn);
        });
    }
}
