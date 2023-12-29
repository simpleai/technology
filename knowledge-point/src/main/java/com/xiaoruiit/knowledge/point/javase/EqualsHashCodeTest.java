package com.xiaoruiit.knowledge.point.javase;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
public class EqualsHashCodeTest {
    public static void main(String[] args) {
        /**
         * 引用在栈上，两个引用指向同一个对象实例时，引用相等。
         * 默认情况下，equals()方法比较的是两个对象的引用是否相等，即是否指向同一个对象实例。
         * 默认情况下，hashCode()方法返回的是堆上的实例对象的哈希值。相同的对象实例内容，hashCode()返回不同。
         * HashSet、HashMap、HashTable等集合类，都会先根据对象的hashCode()方法来判断对象是否相等，不相等时，放入集合中；相等时，再根据equals()方法来判断是否真的相等。
         */
        Test str = new Test();

        System.out.println(str == str);

        Test str2 = new Test();
        System.out.print("str == str2:");
        System.out.println(str == str2);
        System.out.print("str.equals(str2):");
        System.out.println(str.equals(str2));
        System.out.println(str.hashCode());
        System.out.println(str2.hashCode());

        Test str3 = str;
        System.out.print("str == str3:");
        System.out.println(str == str3);

        Object obj = new Object();
        System.out.println(obj.hashCode());

        Object obj2 = new Object();
        System.out.println(obj2.hashCode());
    }
}

@Getter
@Setter
class Test {

    private String name;

    private List<TestItem> testItems;

    @Data
    public class TestItem{
        private String itemName;
    }
}
