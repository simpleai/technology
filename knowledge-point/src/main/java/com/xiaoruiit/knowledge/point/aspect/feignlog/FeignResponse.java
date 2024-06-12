package com.xiaoruiit.knowledge.point.aspect.feignlog;//package com.sexytea.pss.config;
//
//import feign.Response;
//import feign.codec.Decoder;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//
///**
// * @author hanxiaorui
// * @date 2024/6/7
// */
//@Slf4j
//@RefreshScope
//public class FeignResponse implements Decoder {
//
//    @Value("${feign-log.turn-on}")
//    private boolean turnOn;
//
//    @Value("${feign-log.extra-long-truncation}")
//    private boolean extraLongTruncation;
//
//    final Decoder delegate;
//
//    public FeignResponse(Decoder delegate) {
//        Objects.requireNonNull(delegate, "Decoder must not be null. ");
//        this.delegate = delegate;
//    }
//
//    @Override
//    public Object decode(Response response, Type type) throws IOException {
//        if (!turnOn) {
//            return delegate.decode(response, type);
//        }
//
//        try {
//            // 获取Content-Type
//            String contentType = response.headers().getOrDefault("Content-Type", Collections.singleton("")).toString();
//
//            // 如果是文件类型，不打印日志，直接返回
//            if (isFileContentType(contentType)) {
//                return delegate.decode(response, type);
//            }
//
//            String resultStr = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
//            if (extraLongTruncation && resultStr.length() > 4000) {// 超长截断
//                resultStr = resultStr.substring(0, 4000);
//            }
//
//            log.info("Result:{}", resultStr);
//            // 回写body,因为response的流数据只能读一次，这里回写后重新生成response
//            return delegate.decode(response.toBuilder().body(resultStr, StandardCharsets.UTF_8).build(), type);
//        } catch (Exception e) {
//            log.error("feign打印响应日志错误:{}", e);
//            return delegate.decode(response, type);
//        }
//    }
//
//    // 判断Content-Type是否是文件类型
//    private boolean isFileContentType(String contentType) {
//        List<String> fileContentTypes = Arrays.asList("image/", "application/octet-stream", "text/", "application/pdf", "video/", "audio/");
//        for (String fileContentType : fileContentTypes) {
//            if (contentType.startsWith(fileContentType)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
