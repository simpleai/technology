package com.xiaoruiit.knowledge.point.preventingDuplicates.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoruiit.common.domain.ApiCodes;
import com.xiaoruiit.common.domain.BizException;
import com.xiaoruiit.knowledge.point.preventingDuplicates.common.RequestIdStore;
import com.xiaoruiit.knowledge.point.preventingDuplicates.common.CachedBodyHttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PreventingDuplicatesInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(PreventingDuplicatesInterceptor.class);
    @Autowired
    private RequestIdStore requestIdStore;
    private static final long DEFAULT_TTL = 60L;

    public PreventingDuplicatesInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = this.getRequestIdFromBody((CachedBodyHttpServletRequest)request);
        if (StringUtils.isNotBlank(requestId)) {
            boolean success = this.requestIdStore.acquireLock(requestId, DEFAULT_TTL);
            if (!success) {
                throw new BizException(ApiCodes.RESUBMIT.getCode(), ApiCodes.RESUBMIT.getMsg());
            }
        }

        return true;
    }

    /**
     * 请求完成处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     * @see ExceptionRecorderResolver#resolveException(HttpServletRequest, HttpServletResponse, Object, Exception) 被spirngmvc吞了的异常
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            String requestId = this.getRequestId(request);
            if (StringUtils.isNotBlank(requestId)) {
                if (null == ex) {
                    ex = (Exception)request.getAttribute("RECORDED_EXCEPTION");
                }

                if (ex != null) {
                    this.requestIdStore.releaseLock(requestId);
                    // slf4j 识别最后一个ex 参数是异常，会显示异常信息。前边两个参数会填充到日志中
                    this.log.error("请求处理发生异常，已清除请求ID标记，允许重试。requestId: {}, 异常: {}", new Object[]{requestId, ex.getMessage(), ex});
                }
            }
        } catch (Exception var6) {
            Exception e = var6;
            this.log.error("请求完成处理时发生异常", e);
        }

    }

    @Nullable
    private String getRequestId(HttpServletRequest request) throws IOException {
        String requestId = this.getRequestIdFromBody((CachedBodyHttpServletRequest)request);
        return requestId;
    }

    private String getRequestIdFromBody(CachedBodyHttpServletRequest requestWrapper) {
        try {
            String body = IOUtils.toString(requestWrapper.getInputStream(), "UTF-8");
            if (StringUtils.isBlank(body)) {
                return null;
            } else {
                JSONObject jsonObject = JSON.parseObject(body);
                return jsonObject.getString("requestId");
            }
        } catch (Exception var4) {
            Exception e = var4;
            this.log.error("从请求体获取requestId失败", e);
            return null;
        }
    }
}
