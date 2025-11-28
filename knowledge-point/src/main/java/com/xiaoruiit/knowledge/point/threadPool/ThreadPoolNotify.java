package com.xiaoruiit.knowledge.point.threadPool;

import com.xiaoruiit.knowledge.point.threadPool.entity.ThreadPoolConfig;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolNotify {

    /**
     * lark通知
     * @param poolName
     * @param config
     */
    public void updateNotify(String poolName, ThreadPoolConfig config, boolean updateIsSuccess) {
        if (!config.getHaveLarkNotify()) {
            return;
        } else {
            if (updateIsSuccess) {
                if (config.getLarkNotifyUrl() == null){
                    NoticeUtil.send("线程池：" + poolName + "更新成功");
                } else {
                    NoticeUtil.send(config.getLarkNotifyUrl(),"线程池：" + poolName + "更新成功", null);
                }
            } else {
                if (config.getLarkNotifyUrl() == null){
                    NoticeUtil.send("线程池：" + poolName + "更新失败");
                } else {
                    NoticeUtil.send(config.getLarkNotifyUrl(),"线程池：" + poolName + "更新失败", null);
                }
            }
        }
    }


}

class NoticeUtil {
    public static void send(String msg) {
        // lark通知
    }
    public static void send(String url, String msg, String title) {
        // lark通知
    }
}