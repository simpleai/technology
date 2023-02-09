package com.xiaoruiit.knowledge.point.cache.guava;

import com.xiaoruiit.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanxiaorui
 * @date 2022/6/28
 */
@RestController()
@RequestMapping("/user")
public class GuavaUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserVo")
    public Result<UserVo> getUserVo(@RequestParam("userCode") String userCode){
        UserVo userVoCache = userService.getUserVoCache(userCode);
        return Result.success(userVoCache);
    }
}
