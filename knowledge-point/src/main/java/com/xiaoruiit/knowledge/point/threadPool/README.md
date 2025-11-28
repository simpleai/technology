# 动态线程池方案

## 现有问题

## 目的

## 方案

### 线程池默认配置 √

### 告警&通知

### 参数大改小、修改队列大小实现 todo

### 传入自定义拒绝策略 todo

### 线程池状态接入grafa。 grafa没有开放接入能力
可http接口访问

## 使用文档

### 使用线程池
1. 导包
```xml

```
2. 配置线程池
```yaml
thread-pools:
  configs:
    # 线程池名称 poolName
    defaultPool: 
      corePoolSize: 8
      maxPoolSize: 16
      queueCapacity: 0
      keepAliveTime: 60
      rejectedPolicy: CallerRunsPolicy
    purchaseOrder: 
      corePoolSize: 5
```

默认线程池参数：
- corePoolSize: CPU * 2
- maxPoolSize: corePoolSize * 2
- queueCapacity: 0 (同步队列)
- keepAliveTime: 60s
- rejectedPolicy: CallerRunsPolicy(主线程执行)
- haveLarkNotify: true
- larkNotifyUrl: # lark通知地址 默认机器人

3. 获取线程池
```java
// 获取默认配置线程池，第一次获取时创建。支持后续动态修改参数
ThreadPoolExecutor executor = DynamicThreadPoolManager.getThreadPoolExecutor(); // 获取的是名称为defaultPool的线程池
// 获取已经配置的线程池
ThreadPoolExecutor executor = DynamicThreadPoolManager.getThreadPoolExecutor(poolName);
```
4. 动态修改线程池参数

修改或新建nacos配置

### 获取线程池状态

获取服务全部线程池状态http接口示例： GET  https://{地址}/thread-pool/status

controller类地址 ThreadPoolStatusController.allStatus

返回结果示例：
```json
{
    "purchaseOrder": {
        "corePoolSize": 5,
        "maxPoolSize": 16,
        "activeCount": 0,
        "queueSize": 0,
        "taskCount": 0,
        "completedTaskCount": 0,
        "keepAliveTimeSeconds": 60,
        "rejectedHandler": "CallerRunsPolicy"
    },
    "defaultPool": {
        "corePoolSize": 4,
        "maxPoolSize": 16,
        "activeCount": 0,
        "queueSize": 0,
        "taskCount": 0,
        "completedTaskCount": 0,
        "keepAliveTimeSeconds": 60,
        "rejectedHandler": "CallerRunsPolicy"
    }
}
```

### 其他说明：
1. 线程池创建在每个服务内部
2. 每个线程名称是 poolName + 自增整型值
3. 删除、新增线程池配置，重启服务器方可生效
4. 无法动态修改队列大小
5. 当前使用中的最大线程=16，修改为4，可能修改失败


