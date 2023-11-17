package com.xiaoruiit.knowledge.point.jvm;

/**
 * @author hanxiaorui
 * @date 2023/11/14
 */
public class ExecptionTest {
    // 编译
    // javac -encoding UTF-8 ExecptionTest.java
    // 查看字节码文件
    // javap -v .\ExecptionTest.class 查看异常表，增加异常后，需要重新编译

    public static void main(String[] args) {
        try{
            Exception e = new Exception();
            System.out.println("异常栈的开始位置是新建异常行所在位置");
            throw e;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
