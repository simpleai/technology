package com.xiaoruiit.knowledge.point.aspect.feignlog;//package com.sexytea.pss.config;
//
//import feign.codec.Decoder;
//import feign.optionals.OptionalDecoder;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
//import org.springframework.cloud.openfeign.support.SpringDecoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author hanxiaorui
// * @date 2024/6/7
// */
//@Configuration
//public class SupportAutoConfiguration {
//
//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;
//
////    @Bean
//    public Decoder feignDecoder() {
//        return new FeignResponse(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))));
//    }
//}
