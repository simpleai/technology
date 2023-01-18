package com.xiaoruiit.knowledge.point.multithread;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/7/6
 */
public interface OrderCargoFeignClient {

    List<OrderCargoDto> getBatchByCodes(String branchCode, List<String> childSkuCodes);
}
