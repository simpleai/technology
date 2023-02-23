package com.xiaoruiit.knowledge.point.mybatis;

import com.xiaoruiit.common.utils.JSON;
import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import com.xiaoruiit.knowledge.point.multidatasource.datasource.domain.Datasource;
import com.xiaoruiit.knowledge.point.multidatasource.datasource.mapper.DatasourceMapper;
import com.xiaoruiit.knowledge.point.multidatasource.datasource2.domain.Datasource2;
import com.xiaoruiit.knowledge.point.multidatasource.datasource2.mapper.Datasource2Mapper;
import com.xiaoruiit.knowledge.point.mybatis.domain.User;
import com.xiaoruiit.knowledge.point.mybatis.mapper.UserMapper;
import com.xiaoruiit.knowledge.point.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(
        classes = KnowledgePointApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class BatchInsertThenGetIdsSourceTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void getAutoIncrementId() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUserCode("code" + i);

            users.add(user);
        }
        userMapper.batchInsert(users);

        log.warn(JSON.toJSONString(users));
    }

    @Resource
    private UserService userService;

    @Test
    void transactionalTest() {
        userService.insert();
    }


}
