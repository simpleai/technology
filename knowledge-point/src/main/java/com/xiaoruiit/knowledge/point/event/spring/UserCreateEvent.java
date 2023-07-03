package com.xiaoruiit.knowledge.point.event.spring;

import org.springframework.context.ApplicationEvent;

/**
 * @author hanxiaorui
 * @date 2023/5/12
 */
public class UserCreateEvent extends ApplicationEvent {

    private User user;

    public UserCreateEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
