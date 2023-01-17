package com.xiaoruiit.knowledge.point.aspect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanxiaorui
 * @date 2021/12/22
 */

@RestController()
@RequestMapping("/demo/user")
public class DemoUserController {

    @GetMapping("/getUser")
    public String demoUser(@RequestParam("userId") String userId){
        return "zs";
    }
}
