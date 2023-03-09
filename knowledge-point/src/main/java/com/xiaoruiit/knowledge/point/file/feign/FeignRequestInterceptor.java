package com.xiaoruiit.knowledge.point.file.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author hanxiaorui
 * @date 2023/3/6
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    public static String oaUserId = "";

    @Override
    public void apply(RequestTemplate template) {
        // template.header("token","token");
    }

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    /**
     * 和文件有关
     */
    @Bean
    public Encoder feignFormEncoder(){
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}
