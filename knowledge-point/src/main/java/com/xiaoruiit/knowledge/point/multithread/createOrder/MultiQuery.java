package com.xiaoruiit.knowledge.point.multithread.createOrder;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Component
public class MultiQuery {

    @Autowired
    private ThreadPoolExecutor threadPool;
    public CompletableFuture<Map<String, MaterialInfo>> getMaterialBySkuCodes(List<String> skuCodes) {
        // todo skuCodes分批
        return CompletableFuture.supplyAsync(() -> {

            List<List<String>> partition = Lists.partition(skuCodes, 5);
            List<CompletableFuture<Map<String, MaterialInfo>>> result = new ArrayList<>();
            for (List<String> childSkuCodes : partition) {
                CompletableFuture<Map<String, MaterialInfo>> mapCompletableFuture = CompletableFuture.supplyAsync(() -> {
                    Map<String, MaterialInfo> materialInfoMap = new HashMap<>();
                    for (String sku : childSkuCodes) {
                        materialInfoMap.put(sku, new MaterialInfo(sku, "MaterialName-" + sku));
                    }
                    return materialInfoMap;
                }, threadPool);

                result.add(mapCompletableFuture);
            }

            CompletableFuture.allOf(result.toArray(new CompletableFuture[0])).join();
            try {
                return result.stream().map(CompletableFuture::join).flatMap(map -> map.values().stream()).collect(Collectors.toMap(MaterialInfo::getSkuCode, info -> info));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }, threadPool);
    }

    public CompletableFuture<Map<String, MaterialCategory>> getMaterialCategoryBySkuCodes(List<String> skuCodes) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, MaterialCategory> categoryMap = new HashMap<>();
            for (String sku : skuCodes) {
                categoryMap.put(sku, new MaterialCategory(sku, "Category-" + sku));
            }
            return categoryMap;
        });
    }

    public List<MaterialWrapper> getMaterialWrapper(List<String> skuCodes){
        // 异步查询  todo 超时时间设置
        CompletableFuture<Map<String, MaterialInfo>> completableFuture = getMaterialBySkuCodes(skuCodes);
        CompletableFuture<Map<String, MaterialCategory>> completableFuture2 = getMaterialCategoryBySkuCodes(skuCodes);

        // 合并结果
        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFuture2, completableFuture);
        allOf.join();// 等待所有任务完成

        try {
            // 获取异步查询结果
            Map<String, MaterialInfo> stringMaterialInfoMap = completableFuture.get();
            Map<String, MaterialCategory> stringMaterialCategoryMap = completableFuture2.get();

            // 构造包装对象
            return skuCodes.stream()
                    .map(sku -> new MaterialWrapper(
                            sku,
                            stringMaterialInfoMap.get(sku).getSkuName(),
                            stringMaterialCategoryMap.get(sku).getCategoryName()
                    ))
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

@Configuration
class ExcutorUtil {
    @Bean("threadPool")
    public ThreadPoolExecutor get(){
        return new ThreadPoolExecutor(10,// 核心线程数，远大于CPU核数，I/O任务
                20,
                30L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(), // 队列长度为0，提升实时性
                new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略：调用当前线程执行任务。 可以接受少量的接口响应慢
    }
}



@Data
@NoArgsConstructor
@AllArgsConstructor
class MaterialInfo {
    private String skuCode;
    private String skuName;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class MaterialCategory {
    private String skuCode;
    private String categoryName;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class MaterialWrapper {
    private String skuCode;
    private String skuName;
    private String categoryName;
}

