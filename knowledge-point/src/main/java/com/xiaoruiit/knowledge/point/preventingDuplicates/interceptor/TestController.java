package com.xiaoruiit.knowledge.point.preventingDuplicates.interceptor;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/pre")
public class TestController {

    @PostMapping("/add2")
    public String add(String requestId) throws Exception {
        return "success";
    }
}
