package com.xiaoruiit.knowledge.point.jvm;

/**
 * @author hanxiaorui
 * @date 2023/11/15
 */
public class Foo {
    public Foo(){
        System.out.println("new 子类对象时，先执行父类构造方法，再执行子类构造方法");
    }

    public static void main(String[] args) {
        FooSun sun = new FooSun();
    }
}

class FooSun extends Foo{
    public FooSun(){
        System.out.println("FooSun");
    }
}

