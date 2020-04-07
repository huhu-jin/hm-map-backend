package com.jin.humap.config.security;


import com.jin.humap.exception.ExceptionCode;
import com.jin.humap.exception.SystemException;
import com.jin.humap.service.AuthorizationService;
import com.jin.humap.until.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final AuthorizationService authorizationService;

    @Before("@annotation(com.jin.humap.config.security.Authorize)")
    public void checkPermission(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 查询用户拥有权限
        Long accountId = JWTUtil.getUserIdFromRequest(request);
        Set<String> authorization = authorizationService.findByAccountId(accountId);
        Authorize authorize = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Authorize.class);
        String[] needAuthorization = authorize.value();
        if (needAuthorization.length == 0) return;
        if (authorization != null && !authorization.isEmpty()) {
            if (!authorization.containsAll(Arrays.asList(needAuthorization))) {
                // 无操作权限
                throw new SystemException(ExceptionCode.NO_PERMISSION);
            }
        } else {
            throw new SystemException(ExceptionCode.NO_PERMISSION);
        }
    }
}
