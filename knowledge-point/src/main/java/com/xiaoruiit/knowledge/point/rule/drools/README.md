# 规则引擎drools
1. drools idea插件
2. 导包
```xml
        <!--drools-->
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-core</artifactId>
            <version>7.23.0.Final</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-compiler</artifactId>
            <version>7.23.0.Final</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-decisiontables</artifactId>
            <version>7.23.0.Final</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-templates</artifactId>
            <version>7.23.0.Final</version>
        </dependency>
        <dependency>
            <groupId>org.kie</groupId>
            <artifactId>kie-api</artifactId>
            <version>7.23.0.Final</version>
        </dependency>
        <!--drools结束-->
```
3. 基于配置文件
   1. 实体类Person
   2. 配置kmodule文件 META-INF/kmodule.xml
   3. 规则配置。resources/rules/test.drl
   4. 测试类 com.xiaoruiit.knowledge.point.rule.TestDrools.test
4. 基于动态加载
   1. 实体类Person
   2. 测试类 com.xiaoruiit.knowledge.point.rule.TestDrools.ruleStringTest

