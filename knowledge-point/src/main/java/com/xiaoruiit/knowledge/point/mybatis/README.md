# 批量插入后获取插入的自增id
## 包
保证最终mybatis的包大于3.3.1
<dependency>
<groupId>org.mybatis</groupId>
<artifactId>mybatis</artifactId>
</dependency>
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.13</version>
</dependency>
```
## Mapper
```java
public interface UserMapper {

    /**
     * 批量插入，并返回插入的主键；返回的主键在User的id上
     * 不能用@Param，或者只能是@Param("list")
     */
    void batchInsert(List<User> user);
}
```
## Mapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xiaoruiit.knowledge.point.mybatis.mapper.UserMapper">

    <resultMap type="com.xiaoruiit.knowledge.point.mybatis.domain.User" id="userMap">
        <result property="id" column="id" jdbcType="INTEGER"/>
        <result property="userCode" column="user_code" jdbcType="VARCHAR"/>
    </resultMap>
    <!--必须有useGeneratedKeys="true" keyProperty="id"；不能用on duplicate key update，否则只能返回第一个id-->
    <insert id="batchInsert" useGeneratedKeys="true" keyProperty="id">
        insert into `t_user`(user_code)
        values
        <foreach item="entity" collection="list" separator=",">
            (#{entity.userCode})
        </foreach>
    </insert>
</mapper>
```

## 配置
Datasource3UserMybatisConfig
application.yml 中的数据源地址和druid连接池配置
