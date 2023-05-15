package com.buzhishi.synctool.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zafir.zhong
 * @description
 * @date Created in 16:49 2023/5/8.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 权限校验异常
     */
    @ExceptionHandler(Exception.class)
    public Object handleAccessDeniedException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',普通异常'{}'", requestURI, e.getMessage());
        return "error request uri "+requestURI;
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                      HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return "error method request uri "+requestURI;
    }


}
