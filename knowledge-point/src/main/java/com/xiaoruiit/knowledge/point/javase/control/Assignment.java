package com.xiaoruiit.knowledge.point.javase.control;

/**
 * 赋值
 * @author hanxiaorui
 * @date 2022/1/22
 */
public class Assignment {
    public static void main(String[] args) {
        int a = 1;
        Assignment assignment = new Assignment();
        Assignment assignment2 = new Assignment();
        // while (a = 2)
        // while (assignment = assignment2)

        int i = 0;
        // 标签，结合continue起到goto作用
        outer:
        while (true){
            System.out.println(1);
            while(true){
                continue outer;
            }
        }

        //System.gc();
        //System.runFinalization();
    }
}
