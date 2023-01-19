package com.xiaoruiit.knowledge.point.cache.guava;

import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hanxiaorui
 * @date 2022/6/28
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = KnowledgePointApplication.class)
public class GuavaCacheTest {
    @Autowired
    UserService userService;

    @Test
    public void testGuavaCache(){
        userService.getUserVoCache("admin");
    }
}
