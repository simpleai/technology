package com.xiaoruiit.knowledge.point.javaconcurrent.base;

/**
 * 并发理论基础13
 * Integer、String、Boolean不适合作为锁对象，有线程不释放锁，会导致其他线程一直到拿不到锁
 * @author hanxiaorui
 * @date 2023/9/6
 */
public class IntegerLockTest {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 1;

        new Thread(() -> {
            synchronized (a){
                System.out.println("线程1获取到a锁");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b){
                System.out.println("线程2在a锁释放后才拿到b锁");
            }
        }).start();
    }
}
