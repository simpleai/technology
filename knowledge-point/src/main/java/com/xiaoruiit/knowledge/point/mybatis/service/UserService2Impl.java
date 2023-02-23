package com.xiaoruiit.knowledge.point.mybatis.service;

import com.xiaoruiit.knowledge.point.mybatis.domain.User;
import com.xiaoruiit.knowledge.point.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2023/2/23
 */
@Service
public class UserService2Impl implements UserService2{

    @Resource
    private UserMapper userMapper;

    @Override
    //@Transactional(rollbackFor = Exception.class,transactionManager = "dataSourceTransactionManager3")
    public int insert() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUserCode("codeUserService2" + i);

            users.add(user);
        }
        userMapper.batchInsert(users);

        throw new RuntimeException("a");

    }
}
