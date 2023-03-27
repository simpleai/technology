package com.xiaoruiit.knowledge.point.file.smms2oss;

import com.xiaoruiit.common.utils.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * md中包含本地图片
 */
@Slf4j
public class MdContainLoaclPicture {

    public static String BASE_PATH = "C:\\Users\\LENOVO\\Desktop\\";
    public static String OLD_MD_FILE_PATH = BASE_PATH + "knowledge";// 含有md文件的文件夹地址，会递归向下寻找

    public static String PACKAGE_PATH = "\\knowledge-point\\src\\main\\java\\com\\xiaoruiit\\knowledge\\point\\file\\smms2oss\\";
    public static String PICTURE_SOURCE_PATH = System.getProperty("user.dir") + PACKAGE_PATH + "pictureSource.json";// 图片来源地址集合。  Ctrl+Alt + L 格式化json
    public static String LOCAL_PICTURE_PATH = System.getProperty("user.dir") + PACKAGE_PATH + "localPicture.json";// 本地图片所在文件地址，之前未上传成功，需手动处理。  Ctrl+Alt + L 格式化json

    public static void main(String[] args) throws IOException {
        // 1.获取md文件的路径
        int level = 1;
        List<String> mdPaths = new ArrayList<>();
        List<String> mdPathList = getMdPathFromMd(new File(OLD_MD_FILE_PATH), level, mdPaths);
        log.info("mdPathList:" + JSON.toJSONString(mdPathList));

        // 2.获取每一个md文件中的图片地址
        Map<String, Set<String>> mdPicturePathMap = getPicturePathFromMdList(mdPathList);
        log.info("mdPicturePathMap:" + JSON.toJSONString(mdPicturePathMap));

        // 3.图片来源集合
        writepictureSource(mdPicturePathMap);

        // 4.md文件中的本地图片地址输出到json文件，准备手动上传一次。
        writeLocalPictureToJsonFile(mdPicturePathMap);
    }

    private static void writepictureSource(Map<String, Set<String>> mdPicturePathMap) {
        // 图片来源set集合
        Set<String> pictureSources = new HashSet<>();
        for (String s : mdPicturePathMap.keySet()) {
            Set<String> urls = mdPicturePathMap.get(s);
            for (String url : urls) {
                pictureSources.add(url.substring(0, 19));
            }
        }
        writeTxt(pictureSources, PICTURE_SOURCE_PATH);
        log.warn("图片来源:{}", JSON.toJSONString(pictureSources));

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
                // 获取当前md文件中的所有图片链接
                matchStrs.stream().map(v -> v.replaceAll(regex, "$2")).forEach(v -> pictures.add(v));
                mdPictures.put(mdPath,pictures);
            } catch (FileNotFoundException e) {
                log.error("error:", e);
                e.printStackTrace();
            } catch (IOException e) {
                log.error("error:", e);
            }
        }
        return mdPictures;
    }

    /**
     * 本地图片地址写入到json文件
     * @return
     */
    public static void writeLocalPictureToJsonFile(Map<String, Set<String>> mdPicturePathMap){
        Map<String, Set<String>> localMdPicturePathMap = new HashMap<>();

        for (String mdPath : mdPicturePathMap.keySet()) {
            Set<String> picturePaths = mdPicturePathMap.get(mdPath);

            if (picturePaths == null || picturePaths.size() == 0) {
                continue;
            }

            Set<String> localPicthrePaths = new HashSet<>();

            for (int i = 0; i < picturePaths.size(); i++) {
                Iterator<String> iterator = picturePaths.iterator();
                while (iterator.hasNext()) {
                    String picturePath = iterator.next();
                    if (picturePath.startsWith("C:") || picturePath.startsWith("D:")){// 收集本地
                        log.warn("本地图片：mdPath:{},picturePath:{}",JSON.toJSONString(mdPath),JSON.toJSONString(picturePath));
                        // 收集本地
                        localPicthrePaths.add(picturePath);
                    }
                }
            }

            if (localPicthrePaths.size() > 0){
                localMdPicturePathMap.put(mdPath, localPicthrePaths);
            }
        }

        writeTxt(localMdPicturePathMap, MdContainLoaclPicture.LOCAL_PICTURE_PATH);
    }

    /**
     * 本地图片地址写到txt文件中，单独处理
     * @param localMdPicturePathMap
     */
    public static void writeTxt(Object localMdPicturePathMap, String path){
        try{
            String word = JSON.toJSONString(localMdPicturePathMap);

            FileOutputStream fileOutputStream = null;
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }

            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(word.getBytes("UTF-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (IOException e){
            log.error("写入失败,数据:{}", JSON.toJSONString(localMdPicturePathMap));
            log.error("error:", e);
        }

    }

}
