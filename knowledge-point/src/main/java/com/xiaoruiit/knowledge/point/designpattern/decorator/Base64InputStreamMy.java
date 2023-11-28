package com.xiaoruiit.knowledge.point.designpattern.decorator;

/**
 * 装饰器模式 输入base64装饰器
 * @author hanxiaorui
 * @date 2023/11/28
 */
public class Base64InputStreamMy extends FilterInputStreamMy{

    public Base64InputStreamMy(InputStreamMy inputStreamMy) {
        super(inputStreamMy);
    }

    @Override
    public void read() {
        super.read();
        System.out.println("base64");
    }

}
