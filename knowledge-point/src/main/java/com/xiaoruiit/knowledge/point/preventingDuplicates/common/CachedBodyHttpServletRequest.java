package com.xiaoruiit.knowledge.point.preventingDuplicates.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

/**
 * 提前在一个 Filter 中 缓存请求体，否则 @RequestBody 和 AOP 中读取会互相冲突！
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws java.io.IOException {
        super(request);
        InputStream is = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(is);
    }

    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(this.cachedBody);
        return new ServletInputStream() {
            public int read() {
                return byteStream.read();
            }
            public boolean isFinished() { return byteStream.available() == 0; }
            public boolean isReady() { return true; }
            public void setReadListener(ReadListener readListener) {}
        };
    }

    public String getCachedBody() {
        return new String(this.cachedBody, StandardCharsets.UTF_8);
    }
}
