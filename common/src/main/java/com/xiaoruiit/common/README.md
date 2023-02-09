# 如何引入此公共包
## 本地引入
1. 使用maven  clean,compile,package,install后。
2. 另一个项目pom.xml 中添加
    ```xml
   <dependency>
        <groupId>com.xiaoruiit</groupId>
        <artifactId>common</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```
