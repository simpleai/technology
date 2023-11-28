package com.xiaoruiit.knowledge.point.designpattern.decorator;

/**
 * 装饰器模式 输入校验装饰器
 * @author hanxiaorui
 * @date 2023/11/28
 */
public class CheckInputStreamMy extends FilterInputStreamMy{

    public CheckInputStreamMy(InputStreamMy inputStreamMy) {
        super(inputStreamMy);
    }

    @Override
    public void read() {
        super.read();
        System.out.println("check");
    }
}
