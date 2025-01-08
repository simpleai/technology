package com.xiaoruiit.knowledge.point.designpattern.templete;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
public class ReceBarcode implements Scan{
    @Override
    public BarcodeInfo getBarcodeInfoByBarcode(String code) {
        BarcodeInfo barcodeInfo = new BarcodeInfo();
        barcodeInfo.setCode(code);
        if (code.startsWith("69")){
            barcodeInfo.setBarcodeType(BarcodeInfo.BarcodeType.B69);
        }
        return barcodeInfo;
    }
}
