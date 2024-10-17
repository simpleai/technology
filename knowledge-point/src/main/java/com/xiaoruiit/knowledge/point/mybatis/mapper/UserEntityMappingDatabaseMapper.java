package com.xiaoruiit.knowledge.point.mybatis.mapper;

import com.xiaoruiit.knowledge.point.mybatis.domain.UserEntityMappingDatabase;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2023/2/13
 */
public interface UserEntityMappingDatabaseMapper {

    void insert(UserEntityMappingDatabase user);
    List<UserEntityMappingDatabase> query(UserEntityMappingDatabase user);
    public List<UserEntityMappingDatabase> query2();
}
