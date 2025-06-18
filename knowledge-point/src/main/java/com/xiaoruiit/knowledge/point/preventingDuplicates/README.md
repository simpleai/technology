# 防重-防止重复提交

问题：前端代码无法控制只能点击一次；
方案：
1. 前端请求新增类接口时，增加requestId；

2. 后端，判断requestId是否重复，重复则返回“重复提交，请稍后再试”；
两种方式：
3. 
第一种：拦截器+异常处理 HandlerInterceptor + HandlerExceptionResolver

1. 拦截器处理
   1. 业务处理前。从前端参数获取requestId，判断requestId是否已经存在于redis，重复则返回“重复提交，请稍后再试”；不重复则将requestId保存到redis中，并设置过期时间。 
   2. 执行业务
   3. 业务执行完成。如果有异常，删除redis中的requestId。打印错误日志
2. 各业务服务配置拦截新增请求地址

第二种：AOP + 注解
1. 注解定义
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Idempotent {
    String key() default "body.requestId";
    long ttl() default 60; // 秒
}
```

2. AOP
```java
@Aspect
@Component
public class IdempotentAspect {

    @Autowired
    private RequestIdStore requestIdStore;

    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        // 解析 requestId
        String requestId = parseKey(joinPoint, idempotent.key());
        long ttl = idempotent.ttl();

        if (StringUtils.isBlank(requestId)) {
            log.warn("requestId is null");
            // 没有 requestId，跳过幂等控制
            return joinPoint.proceed();
        }

        if (!requestIdStore.acquireLock(requestId, ttl)) {
            throw new RuntimeException("重复请求，请勿重复提交！");
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            // 发生异常才释放锁
            requestIdStore.releaseLock(requestId);
            throw ex;
        }
        // 正常成功则不释放锁
    }

    private String parseKey(ProceedingJoinPoint joinPoint, String spElKey) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        EvaluationContext context = new StandardEvaluationContext();

        // 参数名、值绑定进 context
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);

            // 如果参数是对象，继续解析其字段
            if (args[i] != null) {
                Class<?> argClass = args[i].getClass();
                Field[] fields = argClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        context.setVariable(paramNames[i] + "." + field.getName(), field.get(args[i]));
                    } catch (IllegalAccessException ignored) {}
                }
            }
        }

        ExpressionParser parser = new SpelExpressionParser();
        Object value = parser.parseExpression(spElKey).getValue(context);
        return value == null ? "" : value.toString();
    }
}
```

3. Controller上加注解
```java
@Idempotent(key = "body.requestId", ttl = 60)
@PostMapping("/order")
public void createOrder(@RequestHeader("requestId") String requestId) {
    ...
}
```




