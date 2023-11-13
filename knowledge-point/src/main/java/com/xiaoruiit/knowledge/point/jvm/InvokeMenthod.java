package com.xiaoruiit.knowledge.point.jvm;

import java.util.Random;

/**
 * 官方建议不要重载可变长方法，因为会引起歧义
 * @author hanxiaorui
 * @date 2023/11/10
 */
public class InvokeMenthod {

    public void invoke(Object o, Object... args) {
        System.out.println("invoke1");
    }

    public void invoke(String s, Object o, Object... args) {
        System.out.println("invoke2");
    }

    public static void main(String[] args) {
        InvokeMenthod invokeMenthod = new InvokeMenthod();
        invokeMenthod.invoke(null, 1);// 倾向于调用参数 null 符合的子类 String
    }
}

/**
 * javap -v InvokeMenthod.class 查看常量池
 */

interface 客户 {
    boolean isVIP();
}

class 商户 {
    public double 折后价格 (double 原价, 客户 某客户) {
        return 原价 * 0.8d;
    }
}

class 奸商 extends 商户 {
    @Override
    public double 折后价格 (double 原价, 客户 某客户) {
        if (某客户.isVIP()) {                         // invokeinterface
            return 原价 * 价格歧视 ();                    // invokestatic
        } else {
            return super. 折后价格 (原价, 某客户);          // invokespecial
        }
    }
    public static double 价格歧视 () {
        // 咱们的杀熟算法太粗暴了，应该将客户城市作为随机数生成器的种子。
        return new Random()                          // invokespecial
                .nextDouble()                         // invokevirtual
                + 0.8d;
    }
}
