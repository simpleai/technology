package com.xiaoruiit.knowledge.point.designpattern.templete;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
public abstract class ReceScan{

    public BarcodeInfo scanHandle(BarcodeInfo barcodeInfo){
        // 3.处理
        process(barcodeInfo);
        // 4.返回
        return barcodeInfo;
    }

    abstract void process(BarcodeInfo barcodeInfo);
}
