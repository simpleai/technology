package com.xiaoruiit.knowledge.point.jvm;

/**
 * @author hanxiaorui
 * @date 2023/11/13
 */
public class MethodTest {
    public static void main(String[] args) {
        Parent.staticMethod();
        Sun.staticMethod();
    }
}

class Parent {
    public void method() {
        System.out.println("parent");
    }

    public static void staticMethod(){
        System.out.println("parent static, not hide");
    }

}

class Sun extends Parent {
    public void method() {
        System.out.println("sun");
    }

    public static void staticMethod(){
        System.out.println("sun static");
    }
}
