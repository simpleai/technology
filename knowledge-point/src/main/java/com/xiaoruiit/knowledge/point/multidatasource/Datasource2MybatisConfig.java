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
 * @author sexytea
 */
@Configuration
@MapperScan(basePackages = {"com.xiaoruiit.knowledge.point.multidatasource.datasource2.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class Datasource2MybatisConfig {
    @Autowired
    private Environment env;

    @Bean("dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource2")// 取yml文件中的 spring.datasource, 也可以用 Environment 中的 getProperty方法
    public DataSource taskDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
        VFS.addImplClass(SpringBootVFS.class);
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.xiaoruiit.konwledge.point.multidatasource.datasource2.domain/*.class");
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/datasource2/*Mapper.xml"));// 创建文件夹不能直接 mapp.user 来创建2个文件夹
        String property = env.getProperty("mybatis.configLocation");
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(property));
        return sessionFactory.getObject();
    }

    @Bean(name = "dataSourceTransactionManager2")
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("dataSource2") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate2")
    public SqlSessionTemplate mainSqlSessionTemplate(@Qualifier("sqlSessionFactory2") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
