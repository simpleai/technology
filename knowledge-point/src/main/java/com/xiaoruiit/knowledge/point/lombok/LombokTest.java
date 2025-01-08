package com.xiaoruiit.knowledge.point.lombok;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hanxiaorui
 * @date 2024/8/10
 */
@Data
@Accessors(chain = true)// 链式调用
public class LombokTest {

    private String name;
    private String age;

    public static void main(String[] args) {

        // @Accessors(chain = true)// 链式调用
        LombokTest hanxiaorui = new LombokTest().setName("hanxiaorui").setAge("18");
        System.out.println(hanxiaorui);
    }
}
