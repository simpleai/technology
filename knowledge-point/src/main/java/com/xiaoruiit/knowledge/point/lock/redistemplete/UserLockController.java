package com.xiaoruiit.knowledge.point.lock.redistemplete;

import com.xiaoruiit.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author hanxiaorui
 * @date 2022/7/25
 */
@Slf4j
@RestController
@RequestMapping("/lock/user")
public class UserLockController {

    @Autowired
    private UserLockService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/addUser")
    public Result create (@RequestBody User user) {
        String lockKey = "lock:create_" + user.getUserCode();
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, 1, 10, TimeUnit.SECONDS);
        if (BooleanUtils.isFalse(locked)) {
            return Result.error("创建任务命中分布式锁，稍后重试");
        }

        try {
            return userService.createTask(user);
        } finally {
            redisTemplate.delete(lockKey);
        }
    }
}
