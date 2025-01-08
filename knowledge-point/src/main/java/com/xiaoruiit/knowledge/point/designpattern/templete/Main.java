package com.xiaoruiit.knowledge.point.designpattern.templete;

/**
 * @author hanxiaorui
 * @date 2023/12/27
 */
public class Main {
    public static void main(String[] args) {
        ReceBarcode receBarcode = new ReceBarcode();
        BarcodeInfo barcodeInfoByBarcode = receBarcode.getBarcodeInfoByBarcode("6911111");

        ReceScan receScan;
        if (BarcodeInfo.BarcodeType.B69.equals(barcodeInfoByBarcode.getBarcodeType())){
            receScan = new ReceB69Scan();
        } else {
            receScan = new ReceUnKnowScan();
        }

        receScan.scanHandle(barcodeInfoByBarcode);
    }
}
