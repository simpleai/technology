package com.xiaoruiit.knowledge.point.javaconcurrent.base;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 并发理论基础12
 * @author hanxiaorui
 * @date 2023/9/5
 */
public class StockUpperLower {

    private final AtomicLong upper = new AtomicLong(0);

    private final AtomicLong lower = new AtomicLong(0);

    public void setUpper(long v){
    // public synchronized void setLower(long v){ // 两个方法上都使用synchronized关键字可以解决竞态条件问题，或者提供一个方法同时修改上限和下限
        if (v < lower.get()){
            throw new IllegalArgumentException("upper < lower");
        }
        upper.set(v);
    }

    public  void setLower(long v){
    // public synchronized void setLower(long v){
        if (v > upper.get()){
            throw new IllegalArgumentException("lower > upper");
        }
        lower.set(v);
    }

    public static void main(String[] args) {
        StockUpperLower stockUpperLower = new StockUpperLower();
        stockUpperLower.setUpper(10);
        stockUpperLower.setLower(2);
        new Thread(() -> {
            stockUpperLower.setUpper(5);
            System.out.println("此线程执行upper.set(v)时，另一个线程执行lower.set(v); 可以越过if的判断条件设置出 upper < lower");
        }).start();

        new Thread(() -> {
            stockUpperLower.setLower(7);
        }).start();



        System.out.println("库存上限：" + stockUpperLower.upper.get());
        System.out.println("库存下限：" + stockUpperLower.lower.get());
    }
}
