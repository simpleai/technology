package com.xiaoruiit.knowledge.point.designpattern.decorator;

import java.io.InputStream;

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
    }
}
