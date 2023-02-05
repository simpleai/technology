package com.xiaoruiit.knowledge.point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class KnowledgePointApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgePointApplication.class, args);
    }

}
