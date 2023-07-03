package com.xiaoruiit.knowledge.point.event.spring.listener;

import com.xiaoruiit.knowledge.point.event.spring.MailService;
import com.xiaoruiit.knowledge.point.event.spring.User;
import com.xiaoruiit.knowledge.point.event.spring.UserCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

/**
 * @author hanxiaorui
 * @date 2023/6/13
 */


@Component
@Slf4j
public class UserEventListener {

    @Autowired
    private MailService mailService;

    // 同步解耦事件
    @EventListener
    public void handleEvent(UserCreateEvent event) {
        User user = event.getUser();
        log.info("EventListener1 ReceivingTaskCreateEvent event with message: " + message);

        mailService.sendMail(user);
    }
}
