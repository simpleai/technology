package com.xiaoruiit.knowledge.point.preventingDuplicates.aop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoruiit.knowledge.point.preventingDuplicates.common.RequestIdStore;
import com.xiaoruiit.knowledge.point.preventingDuplicates.common.CachedBodyHttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class IdempotentAspect {

    @Autowired
    private RequestIdStore requestIdStore;

    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        String key = idempotent.key();
        String requestId = "";

        if (key.startsWith("body.")) {
            String field = key.substring("body.".length());
            requestId = extractFromJsonBody(field);
        } else {
            requestId = resolveSpELKey(joinPoint, key);
        }

        if (StringUtils.isBlank(requestId)) {
            return joinPoint.proceed(); // 没有 requestId，跳过幂等
        }

        long ttl = idempotent.ttl();
        if (!requestIdStore.acquireLock(requestId, ttl)) {
            throw new RuntimeException("重复请求，请勿重复提交！");
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            requestIdStore.releaseLock(requestId);
            throw ex;
        }
    }

    private String extractFromJsonBody(String field) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // JDK16+可以这样  if (request instanceof CachedBodyHttpServletRequest cachedRequest) {
        if (request instanceof CachedBodyHttpServletRequest) {
            CachedBodyHttpServletRequest cachedRequest = (CachedBodyHttpServletRequest) request;
            try {
                String json = cachedRequest.getCachedBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(json);
                JsonNode node = root.get(field);
                return node != null ? node.asText() : null;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private String resolveSpELKey(ProceedingJoinPoint pjp, String spElKey) {
        try {
            EvaluationContext context = new StandardEvaluationContext();
            String[] paramNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
            Object[] args = pjp.getArgs();

            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            ExpressionParser parser = new SpelExpressionParser();
            Object value = parser.parseExpression(spElKey).getValue(context);
            return value == null ? "" : value.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
