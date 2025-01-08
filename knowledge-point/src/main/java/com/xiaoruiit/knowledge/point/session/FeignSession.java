package com.xiaoruiit.knowledge.point.session;

import com.xiaoruiit.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hanxiaorui
 * @date 2024/1/23
 */
@FeignClient(
        name = "knowledge-point",url = "127.0.0.1:8080")
public interface FeignSession {
    @GetMapping("/session/testV2")
    Result sessionTestV2();
}
