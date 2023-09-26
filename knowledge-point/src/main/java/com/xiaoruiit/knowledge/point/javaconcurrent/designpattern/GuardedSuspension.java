package com.xiaoruiit.knowledge.point.javaconcurrent.designpattern;

/**
 * 并发设计模式 31
 * 保护性的暂停，等待唤醒，异步转同步
 * 解决：等待调用异步结果，接着处理
 * 实现原理：A服务A线程调用MQ后，休眠；B服务MQ消费端消费消息后，调用A服务提供的A线程唤醒接口
 * 例子：
 *  1. 去吃饭，服务员告知没有位置；先取号，在门店等待。有位置后再叫号可进入吃饭。
 *  2. dubbo中调用TCP的异步转同步
 *  3. 用户通过浏览器发过来一个请求，会被转换成一个异步消息发送给 MQ，等 MQ 返回结果后，再将这个结果返回至浏览器。
 * 替代方案：
 *  1. MQ同步调用
 */
public class GuardedSuspension {


    public Position get(int number){

    }
}

class Position {
    int total; // 总数
    String positionDesc;
}