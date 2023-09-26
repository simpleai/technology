package com.xiaoruiit.knowledge.point.javaconcurrent.designpattern;

import java.util.HashMap;
import java.util.Map;

/**
 * 并发设计模式28
 * 不变性
 */
public class ImmutabilityTest {

    Map map = new HashMap<String, FlyweightPattern>();
    public static void main(String[] args) {
        Integer a = new Integer(1);
        Integer b = new Integer(1);
        System.out.print("new 不走Integer的缓存：");
        System.out.println(a == b);
        System.out.println((a++) == (b++));

        Integer c = Integer.valueOf(1);
        Integer d = Integer.valueOf(1);
        System.out.println(c == d);
        System.out.println((c++) == (d++));


        FlyweightPattern f1 = FlyweightPattern.getInstance(1);
        FlyweightPattern f2 = FlyweightPattern.getInstance(1);

        System.out.println(f1 == f2);
    }

}

/**
 * 享元模式，增加不可变性
 * - 解决：去除经常使用的不可变重复对象，降低内存占用
 * - 实现原理：创建对象时查询对象池中是否有，有的话，直接获取对象池中的对象；没有的话，创建出对象后，放入对象池中
 * - 例子：Integer类型的创建，JVM启动时，将-128至127创建出来，这个范围的数，使用时直接返回这个数的地址。
 * - 缺点：不适合做锁，对象池中的对象是共有的。
 */
final class FlyweightPattern {
    private int age;

    public int getAge() {
        return age;
    }

    private FlyweightPattern(int age){
        this.age = age;
    }

    public static FlyweightPattern getInstance(int age){
        if (FlyweightPatternCache.map.containsKey(age)){
            return FlyweightPatternCache.map.get(age);
        }

        FlyweightPattern f = new FlyweightPattern(age);
        FlyweightPatternCache.map.put(age, f);

        return f;
    }

    private static class FlyweightPatternCache{
        final static Map<Integer, FlyweightPattern> map = new HashMap<>();
    }
}
