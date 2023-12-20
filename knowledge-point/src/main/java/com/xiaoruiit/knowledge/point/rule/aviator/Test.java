package com.xiaoruiit.knowledge.point.rule.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hanxiaorui
 * @date 2023/12/20
 */
public class Test {
    public static void main(String[] args) {
        tryEvaluator(5);
        tryVar();
        tryCompare();
        tryCustomFunction();
    }

    /**
     * 条件判断
     *
     * @param limit
     */
    private static void tryEvaluator(int limit) {
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("limit", limit);
        boolean result = (boolean) AviatorEvaluator.execute("limit==5", env);
        // true
        System.out.println(result);
    }

    /**
     * 多变量使用
     */
    private static void tryVar() {
        String result = (String) AviatorEvaluator.exec("'hello '+yourName+', I am '+myName", "Tom", "Jerry");
        // hello Tom, I am Jerry
        System.out.println(result);
        System.out.println("tryVar结束");
    }

    /**
     * 数据比较
     */
    private static void tryCompare() {
        Map<String, Object> env = new HashMap<String, Object>();

        Map<String, Object> objA = new HashMap<>();
        objA.put("state", 2);
        objA.put("code", "hhxx");

        Map<String, Object> objB = new HashMap<>();
        objB.put("status", 3);
        objB.put("code", "hhxxTTXS");

        env.put("a", objA);
        env.put("b", objB);


        boolean result = (boolean) AviatorEvaluator.execute("a.state<b.status || a.code==b.code", env);

        // true
        System.out.println(result);
    }

    /**
     * 自定义函数
     */
    private static void tryCustomFunction() {
        //注册函数
        AviatorEvaluator.addFunction(new AddFunction());
        // 3.0
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));
        // 521.0
        System.out.println(AviatorEvaluator.execute("add(add(1, 20), 500)"));
    }

    static class AddFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorDouble(left.doubleValue() + right.doubleValue());
        }

        @Override
        public String getName() {
            return "add";
        }
    }
}

@Data
class PersonDO {
    public String name;
    public int age;

    public PersonDO() {
    }

    public PersonDO(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
