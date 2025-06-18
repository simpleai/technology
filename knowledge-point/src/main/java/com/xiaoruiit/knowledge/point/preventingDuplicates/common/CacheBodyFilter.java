package com.xiaoruiit.knowledge.point.preventingDuplicates.common;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 提前在一个 Filter 中 缓存请求体，否则 @RequestBody 和 AOP 中读取会互相冲突！
 * 结合 com.xiaoruiit.knowledge.point.preventingDuplicates.RequestWrapper
 */
@Component
public class CacheBodyFilter extends OncePerRequestFilter {
    public CacheBodyFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!(request instanceof CachedBodyHttpServletRequest)) {
            request = new CachedBodyHttpServletRequest(request);
        }
        filterChain.doFilter(request, response);
    }
}

