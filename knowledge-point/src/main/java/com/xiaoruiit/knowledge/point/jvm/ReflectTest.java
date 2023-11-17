package com.xiaoruiit.knowledge.point.jvm;

import java.lang.reflect.Method;

/**
 * @author hanxiaorui
 * @date 2023/11/14
 */
public class ReflectTest {

    public static void test(int i){
        new Exception("#" + i).printStackTrace();
    }
    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.xiaoruiit.knowledge.point.jvm.ReflectTest");

        Method method = aClass.getMethod("test", int.class);
        method.setAccessible(true);

        method.invoke(null, 0);// 关闭权限检查

    }
}
