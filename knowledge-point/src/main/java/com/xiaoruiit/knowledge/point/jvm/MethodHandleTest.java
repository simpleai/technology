package com.xiaoruiit.knowledge.point.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author hanxiaorui
 * @date 2023/11/15
 */
public class MethodHandleTest {

    public void testMethodHandle(Object o) {
        System.out.println("testMethodHandle");
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup l = MethodHandles.lookup();

        MethodType methodType = MethodType.methodType(void.class, Object.class);

        MethodHandle methodHandle = l.findVirtual(MethodHandleTest.class, "testMethodHandle", methodType);

        methodHandle.invokeExact(new MethodHandleTest(), new Object());

    }
}
