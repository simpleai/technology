package com.xiaoruiit.knowledge.point.javase;

/**
 * 对象操作
 * @author hanxiaorui
 * @date 2022/1/21
 */
public class ObjectOperate {

    public static void main(String[] args) {

        String s;// 创建引用（句柄、指针）
        new String();// 创建对象
        s = new String();// 建立句柄和对象的联系，这并不是赋值
        s.length(); // 用句柄操作对象
    }
}
