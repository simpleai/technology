package com.xiaoruiit.knowledge.point.javase;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
@Data
public class InnerClass {
    private String name;

    private List<InnerClass.TestItem> testItems;

    @Data
    public class TestItem{// 内部成员类，更适合联系紧密，无需要复用的情况；静态内部类适合和外部类关系不大，独立使用的情况；独立类适合复用性强的情况。
        private String itemName;
    }

    public static void main(String[] args) {
        InnerClass test = new InnerClass();
        test.setName("test");
        InnerClass.TestItem testItem = test.new TestItem();
        testItem.setItemName("testItem");
        test.setTestItems(Lists.newArrayList(testItem));
        System.out.println(test);

        StringBuilder sb = new StringBuilder("abc");
        IntStream chars = sb.chars();
        System.out.println(chars);
    }
}
