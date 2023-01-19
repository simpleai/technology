package com.xiaoruiit.knowledge.point.cache.caffeine;

import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = KnowledgePointApplication.class)
class UserCaffeineServiceImplTest {

    @Autowired
    UserCaffeineService userService;

    @Test
    void findUserById() {
        User user = userService.findUserById("U1");
        System.out.println("findUserById:" + user);
    }

    @Test
    void putUser() {

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserCode("U"+i);
            user.setUserName("张"+i);

            userService.putUser(user);
        }

    }

    @Test
    void clearUserCaffeine() {
        userService.clearUserCaffeine("U1");
    }
}
