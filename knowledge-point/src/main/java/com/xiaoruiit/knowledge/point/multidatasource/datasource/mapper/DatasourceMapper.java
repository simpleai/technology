package com.xiaoruiit.knowledge.point.multidatasource.datasource.mapper;

import com.xiaoruiit.knowledge.point.multidatasource.datasource.domain.Datasource;

public interface DatasourceMapper {

    Datasource selectByPrimaryKey(Long id);

}
