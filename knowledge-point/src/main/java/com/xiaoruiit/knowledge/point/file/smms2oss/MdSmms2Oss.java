package com.xiaoruiit.knowledge.point.file.smms2oss;

/**
 * @author hanxiaorui
 * @date 2023/3/27
 */
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import com.aliyun.oss.*;
import com.aliyun.oss.model.PutObjectResult;
import com.xiaoruiit.common.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 *
 * 举例：https://s2.loli.net/2022/03/01/xp9zWlRL6dearOk.png → https://xiaoruiit.oss-cn-beijing.aliyuncs.com/img/xp9zWlRL6dearOk.png
 */
@Slf4j
public class MdSmms2Oss {

    static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    static final String endpointEnd = "oss-cn-beijing.aliyuncs.com";
    static final String accessKeyId = "";
    static final String accessKeySecret = "";
    static final String bucketName = "xiaoruiit";// Bucket名称
    static final String baseObjectName = "img/";
    static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    public static String BASE_PATH = "C:\\Users\\LENOVO\\Desktop\\";
//    public static String OLD_MD_FILE_PATH = BASE_PATH + "knowledge";
    public static String OLD_MD_FILE_PATH = BASE_PATH + "smms2oss";

//    static final String pattern = "https?://i\\.loli\\.net\\/.*\\.(jpg|jpeg|png|gif|bmp)";
    static final String pattern = "https?://s2\\.loli\\.net\\/.*\\.(jpg|jpeg|png|gif|bmp)";
    static final long sleepTime = 100;// 读取休眠时间，单位：ms
    //

    static int numberOfReplacementImages = 0;

    public static void main(String[] args) throws IOException {
        // 1.获取md文件的路径
        int level = 1;
        List<String> mdPaths = new ArrayList<>();
        List<String> mdPathList = getMdPathFromMd(new File(OLD_MD_FILE_PATH), level, mdPaths);
        log.info("mdPathList:" + JSON.toJSONString(mdPathList));
        for (String mdPath : mdPathList) {
            handleEveryMdFile(mdPath);
        }

        ossClient.shutdown();

        log.warn("替换图片个数:{}", numberOfReplacementImages);
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

    private static void handleEveryMdFile(String mdPath) throws IOException {
        String content = readFile(mdPath);

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);

        boolean haveSmms = false;
        while (m.find()) {
            haveSmms = true;

            String imageUrl = m.group();
            String imgName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            String objectName = baseObjectName + imgName;

            try {
                log.info("mdPath:{},imageUrl:{}",mdPath, imageUrl);
                PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(download(imageUrl)));
                if (putObjectResult.getRequestId() != null){
                    String ossUrl = "https://" + bucketName + "." + endpointEnd + "/" + objectName;
                    content = content.replace(imageUrl, ossUrl);

                    numberOfReplacementImages++;// 统计个数
                } else {
                    throw new RuntimeException("");
                }
            } catch (RuntimeException e){
                log.error(mdPath + "--" + imageUrl);
                log.error("error:{}", e);
            }
        }

        if (haveSmms){
            writeFile(mdPath, content);
        }
    }

    public static String readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    public static void writeFile(String filePath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }

    public static byte[] download(String pictureUrl) throws IOException {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = new URL(pictureUrl);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        connection.setRequestProperty("accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
        connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
        connection.setRequestProperty("sec-fetch-dest", "image");

        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, outputStream);

        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }
}

