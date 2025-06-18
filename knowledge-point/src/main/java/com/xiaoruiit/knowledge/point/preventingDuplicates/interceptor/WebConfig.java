package com.xiaoruiit.knowledge.point.preventingDuplicates.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private PreventingDuplicatesInterceptor requestOnceInterceptor;
    @Autowired
    private ExceptionRecorderResolver exceptionRecorderResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 调拨防重
         * @see TestController#add(String)
         */
        registry.addInterceptor(requestOnceInterceptor).addPathPatterns("/api/transfer/add");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(0,exceptionRecorderResolver);
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }
}
