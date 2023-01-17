package com.xiaoruiit.knowledge.point.aspect;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hanxiaorui
 * @date 2022/7/7
 */
@RpcClient(
        url = "https://cn.bing.com",value = "test"
)
public interface NetworkRecordClient {

    @GetMapping("/")
    void index();
}
