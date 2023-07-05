package com.xiaoruiit.knowledge.point;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KnowledgePointApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    private Long start;

    @Before
    public void before() {
        System.out.println("测试开始------------------");
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("耗时: %d(ms)", duration));
        System.out.println("测试结束------------------");
    }

    @Test
    public void test() {
        log.info("测试");
    }
}
