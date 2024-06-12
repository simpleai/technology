package com.xiaoruiit.knowledge.point.aspect.feignlog;//package com.sexytea.pss.config;
//
//import com.alibaba.fastjson.JSON;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.Collection;
//import java.util.Map;
// // 获取请求体需要用 Encoder 来实现
//@Slf4j
////@Configuration
//// @RefreshScope 会初始化两次本类，执行两次
//public class FeignLoggingInterceptor implements RequestInterceptor {
//
//    @Value("${feign-log.turn-on}")
//    private boolean turnOn;
//
//    @Value("${feign-log.header-log}")
//    private boolean headerLog;
//
//    @Override
//    public void apply(RequestTemplate template) {
//        if (!turnOn) {
//            return;
//        }
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (null == attributes) {
//            return;
//        }
//
//        try {
//            if (headerLog) {// 打印请求头
//                for (Map.Entry<String, Collection<String>> entry : template.headers().entrySet()) {
//                    String headerName = entry.getKey();
//                    for (String headerValue : entry.getValue()) {
//                        log.warn("Header: " + headerName + " = " + headerValue);
//                    }
//                }
//            }
//
//            String mehtodName = template.methodMetadata().method().getName();
//            String targetClassName = template.methodMetadata().method().getDeclaringClass().getName();
//            log.info("feign: " + targetClassName + "#" + mehtodName);
//
//            // 获取请求方法和URL
//            String method = template.method();
//            String url = template.url();
//
//            String urlPre = template.feignTarget().url();
//            log.info("Request: " + method + " " + urlPre + url);
//
//            // 获取请求体
//            Object requestBody = template.body();
//            if (requestBody != null) {
//                log.info("RequestBody: " + JSON.toJSONString(requestBody));
//            }
//        } catch (Exception e) {
//            log.error("feign打印请求日志错误:{}", e);
//        }
//    }
//}
