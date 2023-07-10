package com.xiaoruiit.knowledge.point.aspect.annotation;

import com.xiaoruiit.knowledge.point.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class MyServiceTest extends BaseTest {

    @Autowired
    private MyService myService;

    @Test
    public void testAnnotationAspect(){
        myService.doSomething();

        myService.doSomethingElse();

    }
}
