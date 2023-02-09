package com.xiaoruiit.knowledge.point.multidatasource;

import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import com.xiaoruiit.knowledge.point.multidatasource.datasource.domain.Datasource;
import com.xiaoruiit.knowledge.point.multidatasource.datasource.mapper.DatasourceMapper;
import com.xiaoruiit.knowledge.point.multidatasource.datasource2.domain.Datasource2;
import com.xiaoruiit.knowledge.point.multidatasource.datasource2.mapper.Datasource2Mapper;
import com.xiaoruiit.common.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(
        classes = KnowledgePointApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class MultiDataSourceTest {

    @Resource
    private DatasourceMapper datasourceMapper;

    @Resource
    private Datasource2Mapper datasource2Mapper;

    @Test
    void getAutoIncrementId() {
        Datasource datasource = datasourceMapper.selectByPrimaryKey(1L);

        log.warn(JSON.toJSONString(datasource));

        Datasource2 datasource2 = datasource2Mapper.selectByPrimaryKey(1L);

        log.warn(JSON.toJSONString(datasource2));
    }


}
