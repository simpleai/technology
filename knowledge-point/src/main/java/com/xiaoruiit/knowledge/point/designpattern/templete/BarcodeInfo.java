package com.xiaoruiit.knowledge.point.designpattern.templete;

import lombok.Data;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
@Data
public class BarcodeInfo {

    private String code;

    private BarcodeType barcodeType;

    private List<MaterialDto> materialDtos;

    public enum BarcodeType {
        B69("69", "69码"),
        TAG("TAG", "标签"),
        BOX_FORMAT("BOX_FORMAT", "箱码"),
        ;

        private String code;

        private String desc;

        BarcodeType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
