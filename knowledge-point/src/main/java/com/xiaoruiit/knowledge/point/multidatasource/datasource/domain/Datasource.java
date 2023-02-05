package com.xiaoruiit.knowledge.point.multidatasource.datasource.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Datasource implements Serializable {
    private Long id;

    private String name;

    private static final long serialVersionUID = 1L;
}
