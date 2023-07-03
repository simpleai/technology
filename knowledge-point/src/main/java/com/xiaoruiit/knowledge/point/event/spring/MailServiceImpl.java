package com.xiaoruiit.knowledge.point.event.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author hanxiaorui
 * @date 2023/7/3
 */
@Component
@Slf4j
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(User user) {
        System.out.println("发送邮件给：" + user.getUserCode());
    }
}
