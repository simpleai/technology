package com.xiaoruiit.knowledge.point.TecentMap;

import com.alibaba.fastjson2.JSON;
import com.xiaoruiit.common.domain.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2024/9/20
 */
@Component
@Slf4j
@RefreshScope
public class DriveDistanceMatrixService {

    @Value("${distanceMatrix.drive.key:JZ6BZ-EY4EG-4XAQZ-QKOVB-2OZQO-77BV5}")
    private String key;

    @Value("${distanceMatrix.drive.secretKey:Y5A5HU0mD7f3Gskuq57eb9yWTMuhqkRJ}")
    private String secretKey;

    private static final String mode = "driving";

    @Autowired
    private DistanceMatrixFeignClient feignClient;

    public BigDecimal getDriveDistanceMatrix(DistanceMatrix distanceMatrix) {
        distanceMatrix.setKey(key);
        distanceMatrix.setMode(mode);
        String sig = TecentMapSignaHelper.generateSignatureByGet(distanceMatrix, DistanceMatrixFeignClient.getDistanceMatrixURL, secretKey);

        log.info("request com.xiaoruiit.knowledge.point.TecentMap.DistanceMatrixFeignClient.getDistanceMatrix:{},{},{},{},{}",distanceMatrix.getKey(), distanceMatrix.getMode(), distanceMatrix.getFrom(), distanceMatrix.getTo(), sig);
        TencentMapsResult<DistanceMatrixResult> distanceMatrixResult = feignClient.getDistanceMatrix(distanceMatrix.getKey(), distanceMatrix.getMode(), distanceMatrix.getFrom(), distanceMatrix.getTo(), sig);
        log.info("response com.xiaoruiit.knowledge.point.TecentMap.DistanceMatrixFeignClient.getDistanceMatrix:{}", JSON.toJSONString(distanceMatrixResult));
        if (!distanceMatrixResult.isSuccess()) {
            throw new BizException("地图接口返回报错:" + distanceMatrixResult.getMessage());
        }

        return distanceMatrixResult.getResult().getRows().get(0).getElements().get(0).getDistance();
    }

    public BigDecimal getDriveDistanceMatrixTotal(List<DistanceMatrix> distanceMatrices) {
        if (CollectionUtils.isEmpty(distanceMatrices)){
            return null;
        }

        BigDecimal totalDistance = BigDecimal.ZERO;
        for (DistanceMatrix distanceMatrix : distanceMatrices) {
            totalDistance = totalDistance.add(this.getDriveDistanceMatrix(distanceMatrix));
        }

        return totalDistance;
    }

}
