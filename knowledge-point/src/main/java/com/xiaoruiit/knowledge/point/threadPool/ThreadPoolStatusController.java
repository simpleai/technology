package com.xiaoruiit.knowledge.point.threadPool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/thread-pool")
public class ThreadPoolStatusController {

    /**
     * 查询所有线程池状态
     */
    @GetMapping("/status")
    public Map<String, DynamicThreadPoolManager.ThreadPoolStatus> allStatus() {
        return DynamicThreadPoolManager.getAllPoolStatus();
    }

    /**
     * 查询单个线程池状态
     */
    @GetMapping("/status/{name}")
    public DynamicThreadPoolManager.ThreadPoolStatus poolStatus(@PathVariable("name") String name) {
        return DynamicThreadPoolManager.getPoolStatus(name);
    }
}
