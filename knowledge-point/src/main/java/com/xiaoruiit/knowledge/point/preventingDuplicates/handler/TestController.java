package com.xiaoruiit.knowledge.point.preventingDuplicates.handler;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/transfer")
public class TestController {

    @PostMapping("/add")
    public String add(String requestId) throws Exception {
        return "success";
    }
}
