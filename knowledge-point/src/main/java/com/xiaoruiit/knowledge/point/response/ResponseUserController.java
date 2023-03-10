package com.xiaoruiit.knowledge.point.response;

import com.xiaoruiit.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/12/28
 */
@RestController()
@RequestMapping("/response/user")
public class ResponseUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/code/{code}")
    public Result<List<?>> findByCode(@PathVariable("code") String code, @RequestParam("returnType") UserInfoDtoEnum returnType) {
        return userService.findByCode(code, returnType);
    }
}
