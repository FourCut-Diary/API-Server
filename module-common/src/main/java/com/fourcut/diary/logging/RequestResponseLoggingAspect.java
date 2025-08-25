package com.fourcut.diary.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourcut.diary.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestResponseLoggingAspect {

    private final ObjectMapper objectMapper;
    private static final Logger requestLogger = LoggerFactory.getLogger("com.fourcut.diary.logging.request");
    private static final Logger responseLogger = LoggerFactory.getLogger("com.fourcut.diary.logging.response");
    private static final Logger errorLogger = LoggerFactory.getLogger("com.fourcut.diary.logging.error");

    @Around("execution(* com.fourcut.diary..*Controller.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String clientIP = request.getRemoteAddr();

        String headers = Collections.list(request.getHeaderNames()).stream()
                .map(header -> header + "=" + request.getHeader(header))
                .collect(Collectors.joining(", "));

        String params = Arrays.stream(joinPoint.getArgs())
                .map(arg -> {
                    try {
                        return StringUtil.maskSensitiveFields(objectMapper.writeValueAsString(arg));
                    } catch (JsonProcessingException e) {
                        return arg.toString();
                    }
                })
                .collect(Collectors.joining(", "));

        long start = System.currentTimeMillis();

        try {
            requestLogger.info("[REQUEST] {} {}{} | IP: {} | Headers: {} | Params: {}",
                    method, uri, queryString != null ? "?" + queryString : "", clientIP, headers, params);

            Object result = joinPoint.proceed();

            String responseJson = objectMapper.writeValueAsString(result);
            responseJson = StringUtil.maskSensitiveFields(responseJson);
            long duration = System.currentTimeMillis() - start;

            responseLogger.info("[RESPONSE] {} {} | IP: {} | Response: {} | Time: {}ms",
                    method, uri, clientIP, responseJson, duration);

            return result;
        } catch (Throwable e) {
            long duration = System.currentTimeMillis() - start;
            errorLogger.error("[ERROR] {} {} | IP: {} | Error: {} | Time: {}ms",
                    method, uri, clientIP, e.getMessage(), duration);
            throw e;
        } finally {
            MDC.clear();
        }
    }
}
