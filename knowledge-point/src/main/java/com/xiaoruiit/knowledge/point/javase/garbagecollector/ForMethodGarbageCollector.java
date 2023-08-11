package com.xiaoruiit.knowledge.point.javase.garbagecollector;

import static java.lang.Thread.sleep;

/**
 * @author hanxiaorui
 * @date 2023/8/11
 */
public class ForMethodGarbageCollector {
    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            requestSpace();
        }

        int spaceSize = 1 * 1024 * 1024 * 1024 - 1; // 1GB in bytes
        byte[] byte1 = new byte[spaceSize];
        byte[] byte2 = new byte[spaceSize];
        byte[] byte3 = new byte[spaceSize];
        byte[] byte4 = new byte[spaceSize];
        byte[] byte5 = new byte[spaceSize];
        byte[] byte6 = new byte[spaceSize];
        byte[] byte7 = new byte[spaceSize];
        byte[] byte8 = new byte[spaceSize];
        byte[] byte9 = new byte[spaceSize];
    }

    private static void requestSpace() {// 方法执行完必后内存可回收
        // 申请1G空间
        int spaceSize = 1 * 1024 * 1024 * 1024 - 1; // 1GB in bytes
        byte[] bytes = new byte[spaceSize];
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
