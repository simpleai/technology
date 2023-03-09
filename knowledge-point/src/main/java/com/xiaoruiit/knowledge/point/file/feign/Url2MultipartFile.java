package com.xiaoruiit.knowledge.point.file.feign;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hanxiaorui
 * @date 2023/3/7
 */
public class Url2MultipartFile {

    /**
     * 根据url上传，文件名称从url中取，可能导致报错
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static MultipartFile getMultipartFileByUrl(String url) throws Exception{
        FileItem item = null;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setReadTimeout(30000);
        conn.setConnectTimeout(30000);

        // 设置应用程序要从网络连接读取数据
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        String fileName = "";

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            //获取文件名称
            String newUrl = conn.getURL().getFile();
            if (newUrl != null || newUrl.length() <= 0) {
                newUrl = java.net.URLDecoder.decode(newUrl, "UTF-8");
                int pos = newUrl.indexOf('?');
                if (pos > 0) {
                    newUrl = newUrl.substring(0, pos);
                }
                pos = newUrl.lastIndexOf('/');
                fileName = newUrl.substring(pos + 1);
            }
            //此处获取两次，是因为如果只获取一次的话，获取type类型时，文件大小会损坏变小，所以重新获取一次
            BufferedInputStream bis = null;
            HttpURLConnection conn1 = (HttpURLConnection) new URL(url).openConnection();
            bis = new BufferedInputStream(conn1.getInputStream());
            String type = HttpURLConnection.guessContentTypeFromStream(bis);

            FileItemFactory factory = new DiskFileItemFactory(16, null);
            String textFieldName = "downloadFile";  //此处任务取值

            //log.info("文件名为：" + fileName + "  大小" + (conn.getContentLength()/1024)+"KB" + "   contentType=" + type);
            item = factory.createItem(textFieldName, type, false, fileName);
            OutputStream os = item.getOutputStream();

            int bytesRead;
            byte[] buffer = new byte[1024 * conn.getContentLength()];
            while ((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
        }

        if (item != null) {
            return new CommonsMultipartFile(item);
        }
        return null;
    }
}
