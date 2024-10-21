package com.xiaoruiit.knowledge.point.TecentMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hanxiaorui
 * @date 2024/9/19
 */
@FeignClient(url = "https://apis.map.qq.com/", name = "tecent-map")
public interface DistanceMatrixFeignClient {

    String getDistanceMatrixURL = "/ws/distance/v1/matrix?";
    /**
     * 获取距离矩阵
     * @param key
     * @param mode
     * @param from
     * @param to
     * @param sig
     * @return
     * @see "https://lbs.qq.com/service/webService/webServiceGuide/route/webServiceMatrix"
     */
    @GetMapping("/ws/distance/v1/matrix")
    TencentMapsResult<DistanceMatrixResult> getDistanceMatrix(@RequestParam("key") String key,
                                                              @RequestParam("mode") String mode,
                                                              @RequestParam("from") String from,
                                                              @RequestParam("to") String to,
                                                              @RequestParam("sig") String sig);
}
