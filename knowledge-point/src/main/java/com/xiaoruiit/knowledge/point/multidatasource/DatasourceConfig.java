package com.xiaoruiit.knowledge.point.multidatasource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Mybatis支持*匹配扫描包
 *
 */
@Configuration
@MapperScan(basePackages = {"com.xiaoruiit.knowledge.point.multidatasource.datasource.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {
    @Autowired
    private Environment env;

    @Bean("dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")// 取yml文件中的 spring.datasource, 也可以用 Environment 中的 getProperty方法
    public DataSource taskDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        VFS.addImplClass(SpringBootVFS.class);
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.xiaoruiit.konwledge.point.multidatasource.datasource.domain/*.class");
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/datasource/*Mapper.xml"));// 创建文件夹不能直接 mapp.user 来创建2个文件夹
        String property = env.getProperty("mybatis.configLocation");
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(property));
        return sessionFactory.getObject();
    }

    @Bean(name = "dataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mainSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
