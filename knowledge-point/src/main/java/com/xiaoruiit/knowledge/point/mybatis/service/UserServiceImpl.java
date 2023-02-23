package com.xiaoruiit.knowledge.point.mybatis.service;

import com.xiaoruiit.knowledge.point.mybatis.domain.User;
import com.xiaoruiit.knowledge.point.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService2 userService2;

    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "dataSourceTransactionManager3")
    // 事务传播默认值：支持当前事务，如果不存在则创建一个新事务
    // 事务失效：多数据源默认事务管理器名称不是transactionManager，且没有指定事务管理器名称
    public int insert() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUserCode("codeTransactional" + i);

            users.add(user);
        }
        userMapper.batchInsert(users);


        userService2.insert();

        return 0;
    }
}
