package com.xiaoruiit.knowledge.point.TecentMap;

import lombok.Data;

/**
 * @author hanxiaorui
 * @date 2024/9/19
 */
@Data
public class TencentMapsResult<T> {

    /**
     * @see "https://lbs.qq.com/service/webService/webServiceGuide/status"
     */
    private int status;

    private String message;

    private String request_id;

    private T result;

    public boolean isSuccess() {
        return status == 0;
    }
}
