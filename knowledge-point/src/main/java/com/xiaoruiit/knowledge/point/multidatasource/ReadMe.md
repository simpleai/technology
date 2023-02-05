# multi Data Source

配置多个数据源，不同文件夹的sql对应不同数据源
## 包
```xml
<mysql-connector.version>8.0.20</mysql-connector.version>
<mybatis-springboot.version>2.1.3</mybatis-springboot.version>
<druid.version>1.2.5</druid.version>

<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>${mybatis-springboot.version}</version>
</dependency>
        <!--Mysql数据库驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
<version>${mysql-connector.version}</version>
</dependency>
        <!--集成druid连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
<version>${druid.version}</version>
</dependency>
```
## 配置
### 排除spirng数据源
启动类上加
```java
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
```

### 多个数据源配置config
```java
DatasourceConfig，Datasource2MybatisConfig
```

找不到mapper时，可复制粘贴提示的地址到@MapperScan中

*Mapper.xml位置配置在了yaml文件中
```yaml
mybatis:
  configLocation: classpath:mybatis/mybatis-config.xml
```

### 数据源地址配置在yaml中
```yaml
spring:
  application:
    name: knowledge-point
  profiles:
    active: "@profileActive@"
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/datasource?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai&allowMultiQueries=true
    username: root
    password: 123456
    druid:
      # 初始连接数
      initialSize: 5
      # 最小连接池数量
      minIdle: 20
      # 最大连接池数量
      maxActive: 100
      # 配置获取连接等待超时的时间
      maxWait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      minEvictableIdleTimeMillis: 300000
      # 配置一个连接在池中最大生存的时间，单位是毫秒
      maxEvictableIdleTimeMillis: 900000
      # 配置检测连接是否有效
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      webStatFilter:
        enabled: true
      statViewServlet:
        enabled: true
        # 设置白名单，不填则允许所有访问
        allow:
        url-pattern: /druid/*
        # 控制台管理用户名和密码
        login-username:
        login-password:
      filter:
        stat:
          enabled: true
          # 慢SQL记录
          log-slow-sql: true
          slow-sql-millis: 1000
          merge-sql: true
        wall:
          config:
            multi-statement-allow: true
  datasource2:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/datasource2?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai&allowMultiQueries=true
    username: root
    password: 123456
    druid:
      # 初始连接数
      initialSize: 5
      # 最小连接池数量
      minIdle: 20
      # 最大连接池数量
      maxActive: 100
      # 配置获取连接等待超时的时间
      maxWait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      minEvictableIdleTimeMillis: 300000
      # 配置一个连接在池中最大生存的时间，单位是毫秒
      maxEvictableIdleTimeMillis: 900000
      # 配置检测连接是否有效
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      webStatFilter:
        enabled: true
      statViewServlet:
        enabled: true
        # 设置白名单，不填则允许所有访问
        allow:
        url-pattern: /druid/*
        # 控制台管理用户名和密码
        login-username:
        login-password:
      filter:
        stat:
          enabled: true
          # 慢SQL记录
          log-slow-sql: true
          slow-sql-millis: 1000
          merge-sql: true
        wall:
          config:
            multi-statement-allow: true
```

### mybatis全局配置
```java
mybatis-config.xml
```
## 使用
MultiDataSourceTest
