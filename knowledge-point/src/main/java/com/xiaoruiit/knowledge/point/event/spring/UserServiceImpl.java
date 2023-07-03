package com.xiaoruiit.knowledge.point.event.spring;

import com.xiaoruiit.knowledge.point.event.spring.publish.UserEventPublish;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hanxiaorui
 * @date 2023/7/3
 */

public class UserServiceImpl {

    @Autowired
    private UserEventPublish userEventPublish;

    public void createUser(String userCode) {
        User user = new User();
        user.setUserCode(userCode);

        userEventPublish.publishUserCreateEvent(user);// 同步发布事件，解耦
    }
}
