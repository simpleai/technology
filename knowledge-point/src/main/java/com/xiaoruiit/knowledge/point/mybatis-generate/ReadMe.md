# mybatis逆向工程

## 导包

```java
            <!--mybatis-generator-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <!--配置文件路径，可以写绝对路径，也可以写相对路径-->

                    <configurationFile>
                		<!-- TODO 1. -->
                        src/main/resources/mybatis-generator/generatorConfig.xml
                    </configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <!--数据库驱动-->
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.20</version>
                    </dependency>
                    <!--非官方插件引入-->
                    <dependency>
                        <groupId>com.itfsw</groupId>
                        <artifactId>mybatis-generator-plugin</artifactId>
                        <version>1.3.8</version>
                    </dependency>
                    <!--swagger+mybatis-->
                    <dependency>
                        <groupId>com.spring4all</groupId>
                        <artifactId>swagger-spring-boot-starter</artifactId>
                        <version>1.9.0.RELEASE</version>
                    </dependency>
                    <dependency>
                        <groupId>com.github.misterchangray.mybatis.generator.plugins</groupId>
                        <artifactId>myBatisGeneratorPlugins</artifactId>
                        <version>1.2</version>
                    </dependency>
                </dependencies>
            </plugin>
```
## 生成配置

generatorConfig.xml

## 使用

![image-20221002101715543](https://xiaoruiit.oss-cn-beijing.aliyuncs.com/img/image-20221002101715543.png)
