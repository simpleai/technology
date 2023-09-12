package com.xiaoruiit.knowledge.point.javaconcurrent.base;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 并发理论基础08
 * @author hanxiaorui
 * @date 2023/9/4
 */
public class MonitorSallProduce {
    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    private int count;

    public void sall(){
        lock.lock();
        try {
            while (count == 0){// 竞态条件循环检验
                System.out.println("库存为0，等待进货");
                condition.await();
                System.out.println("被唤醒后，先进入等待队列拿到锁。");
                System.out.println("开始执行await之后的代码");
            }
            count--;
            System.out.println("卖出一件商品，库存为：" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void produce(){
        lock.lock();
        try {
            count+=3;
            System.out.println("进货3件商品，库存为：" + count);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MonitorSallProduce monitorSallProduce = new MonitorSallProduce();

        IntStream.range(0, 6).forEach(i -> {
            new Thread(monitorSallProduce::sall).start();
        });

        IntStream.range(0, 1).forEach(i -> {
            new Thread(monitorSallProduce::produce).start();
        });
    }
}
