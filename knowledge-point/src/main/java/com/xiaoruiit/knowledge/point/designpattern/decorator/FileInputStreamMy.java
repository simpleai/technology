package com.xiaoruiit.knowledge.point.designpattern.decorator;

/**
 * 装饰器模式 被装饰者 文件输入
 * @author hanxiaorui
 * @date 2023/11/27
 */
public class FileInputStreamMy implements InputStreamMy{
    @Override
    public void read() {
        System.out.println("read file");
    }
}
