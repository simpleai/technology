package com.xiaoruiit.knowledge.point.javaconcurrent.base;

/**
 * 并发理论基础09
 * @author hanxiaorui
 * @date 2023/9/5
 */
public class TestInterrupt {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("线程1执行中");
                if (Thread.currentThread().isInterrupted()) {// 未休眠需要主动判断中断状态，休眠时抛出异常并重置中断状态
                    System.out.println("未休眠需要主动判断中断状态");
                    break;
                }
            }
        });
        thread.start();
        thread.interrupt();

        // 休眠时调用interrupt()
        MyThread myThread = new MyThread();
        myThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        myThread.interrupt();

        System.out.println(myThread.isInterrupted());

        myThread.stop();
    }

}

class MyThread extends Thread{
    public void run(){
        while (true) {
            System.out.println("线程2执行中");

            try {
                MyThread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("休眠后，调用interrupt()，抛出InterruptedException异常，并重置中断状态");
                throw new RuntimeException(e);
            }
        }
    }
}
