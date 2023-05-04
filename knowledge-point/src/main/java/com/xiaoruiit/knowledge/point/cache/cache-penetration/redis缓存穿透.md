# redis缓存穿透解决
## jvm锁解决方式
关键代码

```java
        String redisKey = MATERIAL_ITEM + taskNo;
        List<StocktakeTaskItem> items = redisTemplate.opsForValue().get(redisKey);
        if (CollectionUtils.isEmpty(items)){
            synchronized (redisKey.intern()){
                items = redisTemplate.opsForValue().get(redisKey);
                if (CollectionUtils.isEmpty(items)){
                    items = stocktakeTaskItemService.listForceMaster(taskNo);
                    redisTemplate.opsForValue().set(redisKey, items, 1, TimeUnit.HOURS);
                }
            }
        }
```

## 缓存穿透解决参考
https://blog.csdn.net/zyjzyj2/article/details/105027746
