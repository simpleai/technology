package com.xiaoruiit.knowledge.point.jvm;

/**
 * @author hanxiaorui
 * @date 2023/11/21
 */
public class ViewClass {
    public static void main(String[] args) {
        int i = 1;
        i++;

        System.out.println(i);

        method();
    }


    public static void method(){
        System.out.println("method");
    }
}
