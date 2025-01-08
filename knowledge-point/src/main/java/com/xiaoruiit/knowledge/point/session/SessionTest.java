package com.xiaoruiit.knowledge.point.session;

import com.xiaoruiit.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hanxiaorui
 * @date 2024/1/23
 */
@RestController
@RequestMapping("/session")
@Slf4j
public class SessionTest {

    @Autowired
    private FeignSession feignSession;

    @GetMapping("/test")
    Result sessionTest(HttpServletRequest request,
                       HttpServletResponse response) {
        log.error("test:" + request.getSession().getId());

        feignSession.sessionTestV2();

        return Result.success();
    }

    @GetMapping("/testV2")
    Result sessionTestV2(HttpServletRequest request,
                       HttpServletResponse response) {
        log.error("testV2:" + request.getSession().getId());

        return Result.success();
    }
}
