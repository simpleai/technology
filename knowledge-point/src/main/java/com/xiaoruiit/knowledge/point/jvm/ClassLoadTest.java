package com.xiaoruiit.knowledge.point.jvm;

/**
 * @author hanxiaorui
 * @date 2023/11/9
 */
public class ClassLoadTest {
    private ClassLoadTest() {}
    private static class LazyHolder {
        static final ClassLoadTest INSTANCE = new ClassLoadTest();
        static {
            System.out.println("LazyHolder.<clinit>");
        }
    }
    public static Object getInstance(boolean flag) {
        if (flag) return new LazyHolder[2];
        return LazyHolder.INSTANCE;
    }
    public static void main(String[] args) {
        getInstance(true);
        System.out.println("----");
        getInstance(false);
    }
// 观察类加载过程
//    javac -encoding UTF-8 ClassLoadTest.java // 编译
//    java -verbose:class ClassLoadTest
}
