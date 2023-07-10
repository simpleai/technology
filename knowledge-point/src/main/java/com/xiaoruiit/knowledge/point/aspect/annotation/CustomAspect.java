package com.xiaoruiit.knowledge.point.aspect.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author hanxiaorui
 * @date 2023/7/10
 */
@Aspect
@Component
public class CustomAspect {

    @Around("@annotation(com.xiaoruiit.knowledge.point.aspect.annotation.CustomAnnotation)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 在方法执行之前的逻辑
        System.out.println("Before method execution");

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 在方法执行之后的逻辑
        System.out.println("After method execution");

        return result;
    }
}
