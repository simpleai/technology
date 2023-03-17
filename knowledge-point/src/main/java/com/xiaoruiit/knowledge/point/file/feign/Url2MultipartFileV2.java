package com.xiaoruiit.knowledge.point.file.feign;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hanxiaorui
 * @date 2023/3/7
 */
public class Url2MultipartFileV2 {

    /**
     * url转MultipartFile
     * @url:图片URL
     * @fileName:文件名
     * @return:返回的文件
     */
    public static MultipartFile urlToMultipartFile(String url,String fileName) throws IOException {
        File file = null;
        MultipartFile multipartFile = null;
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
        httpUrl.connect();
        file = Url2MultipartFileV2.inputStreamToFile(httpUrl.getInputStream(),fileName);
        multipartFile = Url2MultipartFileV2.fileToMultipartFile(file);
        httpUrl.disconnect();
        return multipartFile;
    }

    public static File inputStreamToFile(InputStream ins, String name) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        OutputStream os = new FileOutputStream(file);
        int len = 8192;
        byte[] buffer = new byte[len];
        int bytesRead;
        while ((bytesRead = ins.read(buffer, 0, len)) != -1){
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();// 超过10240字节的数据会存到磁盘上
        FileItem item = diskFileItemFactory.createItem(file.getName(), "text/plain", true, file.getName());

        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        FileInputStream fis = new FileInputStream(file);
        OutputStream os = item.getOutputStream();
        try {
            int len = 8192;
            while ((bytesRead = fis.read(buffer, 0, len)) != -1){
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            if (os != null){
                os.close();
            }
            if (fis != null){
                fis.close();
            }
        }
        return new CommonsMultipartFile(item);
    }

}
