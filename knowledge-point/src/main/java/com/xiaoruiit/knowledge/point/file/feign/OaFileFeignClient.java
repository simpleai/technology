package com.xiaoruiit.knowledge.point.file.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hanxiaorui
 * @date 2023/3/6
 */
@FeignClient(url = "http://oa.test.com", name = "oa", configuration = FeignRequestInterceptor.class)
public interface OaFileFeignClient {

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile file);

}
