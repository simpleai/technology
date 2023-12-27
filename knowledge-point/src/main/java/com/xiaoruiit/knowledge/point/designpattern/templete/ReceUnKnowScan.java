package com.xiaoruiit.knowledge.point.designpattern.templete;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
public class ReceUnKnowScan extends ReceScan{

    @Override
    void process(BarcodeInfo barcodeInfo) {
        System.out.println("ReceUnKnowScan process");
    }
}
