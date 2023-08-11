package com.xiaoruiit.knowledge.point.javase.init;

/**
 * 类初始化顺序
 * @author hanxiaorui
 * @date 2022/1/24
 */
public class InitOrder {

    static int a;

    int b;

    {
        System.out.println("代码块初始化之前：b:"+b);
        b = 4;
        System.out.println("代码块初始化之后：b:"+b);
    }

    static void initMethod(){
        System.out.println("静态方法初始化之前：a:"+a);
        a = 3;
        System.out.println("静态代码块初始化之后：a:"+a);

        int x;
        // System.out.println(x);// 编译报错
    }

    static {
        System.out.println("静态代码块初始化之前：a:"+a);
        a = 1;
        System.out.println("静态代码块初始化之后：a:"+a);

        int y;
        // System.out.println(y);// 编译报错
    }

    InitOrder(){
        System.out.println("构造方法初始化");
    }

    public static void main(String[] args) {
        //int a = InitOrder.a;

        InitOrder initOrder = new InitOrder();
        System.out.println(initOrder.b);

        //InitOrder.initMethod();
    }
}
