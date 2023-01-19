package com.xiaoruiit.knowledge.point.cache.redis;

import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import com.xiaoruiit.knowledge.point.utils.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = KnowledgePointApplication.class)
class UserRedisServiceImplTest {

    @Autowired
    UserRedisService userService;

    @Test
    void getUserList() {
        List<User> userList = userService.getUserList();
        System.out.println("getUserList:" + JSON.toJSONString(userList));
    }


}
