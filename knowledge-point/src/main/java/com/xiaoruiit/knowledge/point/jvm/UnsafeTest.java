package com.xiaoruiit.knowledge.point.jvm;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author hanxiaorui
 * @date 2023/11/10
 */
@Slf4j
public class UnsafeTest {
    public static void main(String[] args) {
        boolean a = true;
        boolean b = true;
        boolean c = false;

        Unsafe unsafe;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            unsafe.putBoolean(a, 1L, false);// 原值，偏移量，新值
            boolean aBoolean = unsafe.getBoolean(a, 1L);
            System.out.println(aBoolean);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


    }
}
