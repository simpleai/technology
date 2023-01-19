package com.xiaoruiit.knowledge.point.cache.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hanxiaorui
 * @date 2022/6/27
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1;

    private String userCode;

    private String userName;
}
