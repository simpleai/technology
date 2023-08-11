package com.xiaoruiit.knowledge.point.javase.init;

import java.util.Arrays;

/**
 * 数组初始化
 *
 * @author hanxiaorui
 * @date 2022/1/24
 */
public class ArrayInit {

    public static void main(String[] args) {
        int[] ints = new int[3];
        System.out.println(Arrays.toString(ints));
        char[] chars = new char[2];// char没有默认初始化
        System.out.println(Arrays.toString(chars));
        boolean[] booleans = new boolean[3];
        System.out.println(Arrays.toString(booleans));
    }
}
