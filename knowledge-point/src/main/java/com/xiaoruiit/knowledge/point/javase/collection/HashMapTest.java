package com.xiaoruiit.knowledge.point.javase.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hanxiaorui
 * @date 2023/7/5
 */
@Slf4j
public class HashMapTest {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap map = new HashMap(4);

        Class<? extends Map> aClass = map.getClass();

        Field threshold = aClass.getDeclaredField("threshold");
        threshold.setAccessible(true);

        Method capacity = aClass.getDeclaredMethod("capacity");
        capacity.setAccessible(true);

        for (int i = 0; i < 4; i++) {
            map.put("q" + i, "q" + i);
            log.warn("threshold:{}, capacity:{}", threshold.get(map), capacity.invoke(map));
        }

        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMapWithExpectedSize(7);
        System.out.println(objectObjectHashMap.size());;
    }
}
