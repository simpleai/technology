package com.xiaoruiit.knowledge.point.TecentMap;

import com.xiaoruiit.knowledge.point.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Slf4j
public class TecentMapsTest extends BaseTest {

    @Autowired
    private DriveDistanceMatrixService distanceMatrixService;

    @Test
    public void getDistanceMatrix() {
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        distanceMatrix.setFrom("40.220770,116.231280");
        distanceMatrix.setTo("39.996472,116.481631");

        BigDecimal distance = distanceMatrixService.getDriveDistanceMatrix(distanceMatrix);
        log.warn("" + distance);
    }

}
