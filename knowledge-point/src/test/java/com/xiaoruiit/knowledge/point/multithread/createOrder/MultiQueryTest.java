package com.xiaoruiit.knowledge.point.multithread.createOrder;

import com.google.common.collect.Lists;
import com.xiaoruiit.knowledge.point.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MultiQueryTest extends BaseTest {
    @Autowired
    MultiQuery multiQuery;
    @Test
    public void test() {
        List<String> skuCodes = Lists.newArrayList("sku1", "sku2", "sku3", "sku4", "sku5", "sku6", "sku7", "sku8", "sku9", "sku10");
        List<MaterialWrapper> materialWrappers = multiQuery.getMaterialWrapper(skuCodes);
        materialWrappers.forEach(System.out::println);
    }

}