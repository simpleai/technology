package com.xiaoruiit.knowledge.point.designpattern.decorator;

/**
 * 装饰器模式 输入过滤装饰器
 * @author hanxiaorui
 * @date 2023/11/27
 */
public class FilterInputStreamMy implements InputStreamMy {

    private InputStreamMy inputStreamMy;

    public FilterInputStreamMy(InputStreamMy inputStreamMy) {
        this.inputStreamMy = inputStreamMy;
    }

    @Override
    public void read() {
        inputStreamMy.read();
    }
}
