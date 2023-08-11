package com.xiaoruiit.knowledge.point.javase.init;

/**
 * 类内容初始化顺序
 *
 * @author hanxiaorui
 * @date 2022/1/24
 */
class Bowl {
    Bowl(int marker) {
        System.out.println("Bowl(" + marker + ")");// 3 // 5 // 11 // 13 //15 //22 //29
    }

    void f(int marker) {
        System.out.println("f(" + marker + ")");// 8 // 18 //25 //32
    }
}

class Table {
    static Bowl b1 = new Bowl(1); // 2

    Table() {
        System.out.println("Table()");// 6
        b2.f(1);// 7
    }

    void f2(int marker) {
        System.out.println("f2(" + marker + ")"); // 34
    }

    static Bowl b2 = new Bowl(2);// 4
}

class Cupboard {

    {
        System.out.println("普通代码块初始化");
    }

    Bowl b3 = new Bowl(3);// 14 // 21 // 28
    static Bowl b4 = new Bowl(4);// 10

    Cupboard() {
        System.out.println("Cupboard()");// 16 // 23 //30
        b4.f(2);// 17 // //24 //31
    }

    void f3(int marker) {
        System.out.println("f3(" + marker + ")");// 36
    }

    static {
        System.out.println("静态代码块初始化");
    }

    static Bowl b5 = new Bowl(5);// 12




}

public class StaticInitialization {
    public static void main(String[] args) {
        System.out.println(
                "Creating new Cupboard() in main");// 19
        new Cupboard(); // 20
        System.out.println(
                "Creating new Cupboard() in main");// 26
        new Cupboard();// 27
        t2.f2(1);// 33
        t3.f3(1);// 35
    }

    static Table t2 = new Table();// 1
    static Cupboard t3 = new Cupboard();// 9
}
