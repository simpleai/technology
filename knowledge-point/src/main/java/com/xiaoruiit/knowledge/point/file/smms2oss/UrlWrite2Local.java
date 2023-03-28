package com.xiaoruiit.knowledge.point.file.smms2oss;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author hanxiaorui
 * @date 2023/3/27
 */
@Slf4j
public class UrlWrite2Local {
    public static String BASE_PATH = "C:\\Users\\LENOVO\\Desktop\\";
    //    public static String OLD_MD_FILE_PATH = BASE_PATH + "knowledge";
    public static String OLD_MD_FILE_PATH = BASE_PATH + "smms2oss";

    public static void downloadImage(String imageUrl, String destinationFile) throws IOException {
        File file = new File(destinationFile);
        if (!file.exists()) {
            file.createNewFile();
        }

        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        connection.setRequestProperty("accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8");
        connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
        connection.setRequestProperty("sec-fetch-dest", "image");
        InputStream is = connection.getInputStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static void main(String[] args) {
        String imageUrl = "https://i.loli.net/2021/09/09/oWnG3aXUliQmS8Z.png";
        String imgName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String destinationFile = OLD_MD_FILE_PATH + "\\" + imgName;

        try {
            downloadImage(imageUrl, destinationFile);
            System.out.println("Image downloaded successfully!");
        } catch (IOException e) {
            System.out.println("Error while downloading image: " + e.getMessage());
        }

    }
}
