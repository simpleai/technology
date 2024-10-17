package com.xiaoruiit.knowledge.point.mybatis;

import com.xiaoruiit.common.utils.JSON;
import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import com.xiaoruiit.knowledge.point.mybatis.domain.UserEntityMappingDatabase;
import com.xiaoruiit.knowledge.point.mybatis.mapper.UserEntityMappingDatabaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(
        classes = KnowledgePointApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class UserEntityMappingDatabaseTest {

    @Resource
    private UserEntityMappingDatabaseMapper userEntityMappingDatabaseMapper;

    @Test
    public void query() {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, "1", "2");
        UserEntityMappingDatabase build = UserEntityMappingDatabase.builder().roleCodes(set).build();
        List<UserEntityMappingDatabase> query = userEntityMappingDatabaseMapper.query(build);
        log.warn("query result:{}", JSON.toJSONString(query));
    }

    @Test
    public void insert() {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, "1", "2");

        UserEntityMappingDatabase userEntityMappingDatabase = UserEntityMappingDatabase.builder()
                .status(UserEntityMappingDatabase.StatusEnum.TO_BE_REVIEWED)
                .extInfo(UserEntityMappingDatabase.ExtInfo.builder().msg("msg").operateBy("operateBy").build())
                .addressDetails(Collections.singletonList(UserEntityMappingDatabase.AddressDetail.builder().address("address").zipCode("zipCode").build()))
                .roleCodes(set)
                .build();
        userEntityMappingDatabaseMapper.insert(userEntityMappingDatabase);
    }

}
