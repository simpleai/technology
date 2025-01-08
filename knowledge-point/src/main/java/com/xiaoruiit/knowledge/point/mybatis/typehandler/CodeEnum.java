package com.xiaoruiit.knowledge.point.mybatis.typehandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hanxiaorui
 * @date 2024/10/17
 */
public interface CodeEnum {
    String name();

    int code();

    static <T extends CodeEnum> Map<Integer, T> toMap(T[] values) {
        return Collections.unmodifiableMap((Map) Arrays.stream(values).collect(Collectors.toMap(CodeEnum::code, Function.identity())));
    }
}
