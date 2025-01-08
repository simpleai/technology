package com.xiaoruiit.knowledge.point.TecentMap;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2024/9/19
 */
@Data
public class DistanceMatrixResult {

    private List<DistanceMatrixResultItem> rows;

    @Data
    public static class DistanceMatrixResultItem {

        private List<DistanceMatrixResultElement> elements;
    }

    @Data
    public static class DistanceMatrixResultElement {
        private BigDecimal distance;// 单位：米
        private BigDecimal duration;
        private String status;// 4 代表附近没有路，距离计算失败，此时distance为直线距离
    }
}
