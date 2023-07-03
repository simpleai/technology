package com.xiaoruiit.knowledge.point.event.spring.publish;

import com.xiaoruiit.knowledge.point.event.spring.User;
import com.xiaoruiit.knowledge.point.event.spring.UserCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author hanxiaorui
 * @date 2023/6/13
 */
@Component
public class UserEventPublish {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserEventPublish(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishUserCreateEvent(User user) {
        UserCreateEvent event = new UserCreateEvent(this, user);
        eventPublisher.publishEvent(event);
    }
}
