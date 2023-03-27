package com.xiaoruiit.knowledge.point.file.smms2oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.xiaoruiit.common.utils.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sm.ms图片迁移到oss
 */
@Slf4j
public class Smms2Oss {

    public static String BASE_PATH = "C:\\Users\\LENOVO\\Desktop\\";
    public static String OLD_MD_FILE_PATH = BASE_PATH + "smms2oss";

    public static void main(String[] args) {
        // 1.获取md文件的路径
        int level = 1;
        List<String> mdPaths = new ArrayList<>();
        List<String> mdPathList = getMdPathFromMd(new File(OLD_MD_FILE_PATH), level, mdPaths);
        log.info("mdPathList:" + JSON.toJSONString(mdPathList));

        // 2.获取每一个md文件中的smms图片地址
        Map<String, Set<String>> mdPicturePathMap = getPicturePathFromMdList(mdPathList);
        log.info("mdPicturePathMap:" + JSON.toJSONString(mdPicturePathMap));

    }

    /**
     * 从文件夹获取所有的md文件路径
     * @return
     */
    public static List<String> getMdPathFromMd(File f, int level, List<String> mdPaths){
        File[] childs = f.listFiles();// 返回一个抽象路径名数组，这些路径名表示此抽象路径名所表示目录中地文件
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isDirectory()) { //
                getMdPathFromMd(childs[i], level + 1, mdPaths);
            } else {
                String filename = childs[i].getName();// 文件名
                String fileSuffix = filename.substring(filename.lastIndexOf(".") + 1);
                if ("md".equals(fileSuffix)) {
                    String path = childs[i].getPath();
                    mdPaths.add(path);
                }
            }

        }
        return mdPaths;
    }

    /**
     * 从md文件中获取图片地址
     * @return
     */
    public static Map<String,Set<String>> getPicturePathFromMdList(List<String> mdPaths){
        Map<String, Set<String>> mdPictures = new HashMap<>();
        for (String mdPath : mdPaths) {
            Set<String> pictures = new HashSet<>();

            File file = new File(mdPath);
            try (FileReader fr = new FileReader(file)){
                char[] chs = new char[1024];
                int len;
                String str = "";
                while ((len = fr.read(chs)) != -1) {
                    str += new String(chs, 0, len);
                }

                String regex = "!\\[(.*?)\\]\\((.*?)\\)";
                Pattern patten = Pattern.compile(regex);//编译正则表达式
                Matcher matcher = patten.matcher(str);// 指定要匹配的字符串
                List<String> matchStrs = new ArrayList();

                while (matcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
                    matchStrs.add(matcher.group());//获取当前匹配的值
                }
                // 获取当前md文件中的smms图片链接
                matchStrs.stream().map(v -> v.replaceAll(regex, "$2"))
                        .filter(v -> v.startsWith("https://i.loli.net") || v.startsWith("https://s2.loli.net"))
                        .forEach(v -> pictures.add(v));
                if (CollectionUtils.isNotEmpty(pictures)){
                    mdPictures.put(mdPath,pictures);
                }
            } catch (FileNotFoundException e) {
                log.error("error:", e);
                e.printStackTrace();
            } catch (IOException e) {
                log.error("error:", e);
            }
        }
        return mdPictures;
    }


}
