package com.xiaoruiit.knowledge.point.lock.redistemplete;

import com.xiaoruiit.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hanxiaorui
 * @date 2022/7/24
 */
@Service
@Slf4j
public class UserLockServiceImpl implements UserLockService {

    /**
     * 模拟数据库存储数据
     */
    @Override
    public Result createTask(User user) {
        return null;
    }
}
