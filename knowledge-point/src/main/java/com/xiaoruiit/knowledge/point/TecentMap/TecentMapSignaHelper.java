package com.xiaoruiit.knowledge.point.TecentMap;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author hanxiaorui
 * @date 2024/9/24
 */
@Slf4j
public class TecentMapSignaHelper {

    /**
     * get请求获取签名
     * @param obj
     * @return
     * @see "https://lbs.qq.com/faq/serverFaq/webServiceKey"  文档末尾有加签&发请求测试工具地址
     */
    public static String generateSignatureByGet(Object obj, String url, String secretKey) {
        StringBuilder content = new StringBuilder();
        content.append(url);
        Field[] fields = obj.getClass().getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));

        String[] ignoredParams = {"$jacocoData"}; // 忽略的参数名数组，去除测试框架添加的参数

        for (Field field : fields) {
            try {
                field.setAccessible(true); // 允许访问私有变量
                Object fieldValue = field.get(obj);

                if (fieldValue != null && !Arrays.asList(ignoredParams).contains(field.getName())) { // 忽略值为null的字段
                    content.append(field.getName()).append('=').append(fieldValue).append('&');
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field value", e);
            }
        }

        content.delete(content.length() - 1, content.length());

        log.info("加密前无secretKey:" + content);

        content.append(secretKey); // 添加密钥到签名字符串末尾

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(content.toString().getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
