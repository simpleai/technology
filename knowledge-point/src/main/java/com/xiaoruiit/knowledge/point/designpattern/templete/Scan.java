package com.xiaoruiit.knowledge.point.designpattern.templete;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
public interface Scan {

    /**
     * 扫码
     */
    BarcodeInfo getBarcodeInfoByBarcode(String code);

    default String paramHandle(String code) {
        if (code == null || code.length() == 0) {
            throw new RuntimeException("code is null");
        }
        code = code.trim();

        return code;
    }
}
