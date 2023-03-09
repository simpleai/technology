package com.xiaoruiit.knowledge.point.file.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoruiit.common.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hanxiaorui
 * @date 2023/3/9
 */
@Slf4j
@Component
public class Business {

    @Resource
    private OaFileFeignClient oaFileFeignClient;

    /**
     * 将url上传到第三方系统，返回第三方系统中的文件id
     * @param urls
     * @return
     */
    public List<String> uploadFileByUrls(List<String> urls) throws JsonProcessingException {
        List<String> oaIds = new ArrayList<>();

        if (urls == null){
            return new ArrayList<>();
        }

        for (String url : urls) {
            // 下载
            MultipartFile fileItem = null;
            try {
                fileItem = Url2MultipartFile.getMultipartFileByUrl(url);
            } catch (Exception e) {
                log.error("上传文件入参:{},error:{}", JSON.toJSONString(urls),e);
                throw new RuntimeException();
            }

            //上传
            String resultOA = oaFileFeignClient.uploadFile(fileItem);

            // 处理返回值
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> mapResultOA = mapper.readValue(resultOA, Map.class);

            Map<String, Object> dataMap = mapper.readValue(JSON.toJSONString(mapResultOA.get("data")), Map.class);

            if (dataMap.get("fileid") == null){
                log.error("上传文件入参:{},OA返回值:{}", JSON.toJSONString(urls),JSON.toJSONString(mapResultOA));
            }

            String oaId = dataMap.get("fileid").toString();
            oaIds.add(oaId);
        }

        return oaIds;
    }
}
