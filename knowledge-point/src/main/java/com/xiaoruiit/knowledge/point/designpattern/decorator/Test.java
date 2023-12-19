package com.xiaoruiit.knowledge.point.designpattern.decorator;

/**
 * 装饰器模式 测试类，使用demo
 * @author hanxiaorui
 * @date 2023/11/28
 */
public class Test {
    public static void main(String[] args) {
        InputStreamMy inputStreamMy = new FileInputStreamMy();
        inputStreamMy = new Base64InputStreamMy(inputStreamMy);
        inputStreamMy.read();
        inputStreamMy = new CheckInputStreamMy(inputStreamMy);
        inputStreamMy.read();

        String preBlockSpinValue = System.getProperty("XX:PreBlockSpin");
        System.out.println("XX:PreBlockSpin value: " + preBlockSpinValue);
    }
}
