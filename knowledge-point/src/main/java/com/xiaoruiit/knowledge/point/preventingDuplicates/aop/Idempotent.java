package com.xiaoruiit.knowledge.point.preventingDuplicates.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// annotation/Idempotent.java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Idempotent {
    String key() default "body.requestId";
    long ttl() default 60; // 秒
}
