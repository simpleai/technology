package com.xiaoruiit.knowledge.point.aspect.annotation;

import org.springframework.stereotype.Component;

/**
 * @author hanxiaorui
 * @date 2023/7/10
 */
@Component
public class MyService {

    @CustomAnnotation
    public void doSomething() {
        System.out.println("Doing something");
    }

    public void doSomethingElse() {
        System.out.println("Doing something else");
    }
}
