package com.xiaoruiit.knowledge.point.javaconcurrent.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 并发工具包16
 * @author hanxiaorui
 * @date 2023/9/6
 */
public class ObjectPool {
    final ArrayBlockingQueue<ThreadObj> pool;

    final Semaphore semaphore;

    public ObjectPool(int size) {
        pool = new ArrayBlockingQueue<ThreadObj>(size);
        for (int i = 0; i < size; i++) {
            try {
                ThreadObj threadObj = new ThreadObj();
                threadObj.setId(Long.valueOf(i));
                pool.add(threadObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        semaphore = new Semaphore(size);
    }

    public Long exec(Function<ThreadObj,Long> function) throws InterruptedException {
        ThreadObj t = null;
        semaphore.acquire();
        try {
            t = pool.poll();
            Long apply = function.apply(t);
            return apply;
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        // 信号量实现对象池
        ObjectPool pool = new ObjectPool(2);// 初始化线程池大小2
        for (int i = 0; i < 10; i++) {// 模拟10个线程
            new Thread(() -> {
                try {
                    Long exec返回值 = pool.exec(t -> {// 实现exec方法，入参是Function函数式接口，t是ThreadObj的实例化对象，返回Long类型的值
                        try {
                            Thread.sleep(1000);// 每个线程执行需要1秒
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("自己实现的exec的返回值:" + t.getId());

                        return t.getId();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Function<Integer, Function<Integer, Function<Integer, Integer>>> axPlusb = a -> (x -> (b -> (a * x + b)));
        int y = axPlusb.apply(2).apply(3).apply(4);
        System.out.println(y);
    }




    class ThreadObj{
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
