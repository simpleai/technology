package com.xiaoruiit.knowledge.point.preventingDuplicates.aop;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/transfer/aop/test")
public class TestAopController {

    /**
     * body请求示例
     * {
     *   "requestId": "abc123"
     * }
     * @param requestId
     * @return
     * @throws Exception
     */
    @Idempotent
    @PostMapping("/add")
    public String add(String requestId) throws Exception {
        return "success";
    }
}
