package com.xiaoruiit.knowledge.point.preventingDuplicates.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常被spring mvc捕获后，会调用此方法; 结合 HandlerInterceptor.afterCompletion 方法，可以释放资源，打印日志。
 */
@Component
@Order(Integer.MAX_VALUE)
public class ExceptionRecorderResolver implements HandlerExceptionResolver {
    public ExceptionRecorderResolver() {
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        request.setAttribute("RECORDED_EXCEPTION", ex);
        return null;
    }
}

