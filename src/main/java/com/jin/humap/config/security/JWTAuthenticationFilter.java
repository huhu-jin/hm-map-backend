package com.jin.humap.config.security;

import com.alibaba.fastjson.JSONObject;
import com.jin.humap.dto.ApiResponse;
import com.jin.humap.exception.ExceptionCode;
import com.jin.humap.until.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * 权限过滤器 处理401
 */
@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();


    public static List<String> notFilterUrls = new LinkedList<>();

    static {
        notFilterUrls.add("/free/**");
        notFilterUrls.add("/index"); //可使用通配符 /demo/authorize/**
    }

    // 不需要验证的url
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String urlPattern : notFilterUrls) {
            if (antPathMatcher.match(urlPattern, requestURI)) return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (JWTUtil.verifyToken(request)) {
            chain.doFilter(request, response);
        } else {
            // 验证失败
            ServletOutputStream out = response.getOutputStream();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

            String message = JSONObject.toJSONString(ApiResponse.ERROR(ExceptionCode.TOKEN_FAILURE));
            out.write(message.getBytes(Charset.defaultCharset()));
            out.flush();
        }
    }

}