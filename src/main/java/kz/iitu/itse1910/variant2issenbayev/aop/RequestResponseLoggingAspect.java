package kz.iitu.itse1910.variant2issenbayev.aop;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class RequestResponseLoggingAspect {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ObjectMapper objectMapper;

    @Pointcut("within(kz.iitu.itse1910.variant2issenbayev.controller.*) && bean(*Controller)")
    public void isController() {}

    @Pointcut("within(kz.iitu.itse1910.variant2issenbayev.aop.*) && bean(exceptionHandlerDelegate)")
    public void isExceptionHandlerDelegate() {}

    @Before("isController()")
    public void logRequest(JoinPoint joinPoint) {
        String requestDetails = getRequestDetails();
        Optional<Object> requestBodyOptional = getRequestBodyOptional(joinPoint);
        if (requestBodyOptional.isPresent()) {
            requestDetails += "; request body: " + toJson(requestBodyOptional.get());
        }
        log.info("REQUEST: " + requestDetails);
    }

    @After("isController() || isExceptionHandlerDelegate()")
    public void logResponse(JoinPoint joinPoint) {
        if (isMethodNonVoid(joinPoint.getSignature())) {
            return;
        }
        String details = getRequestDetails() + getResponseDetails(null);
        log.info("RESPONSE: " + details);
    }

    @AfterReturning(value = "isController() || isExceptionHandlerDelegate()", returning = "retVal")
    public void logResponse(JoinPoint joinPoint, Object retVal) {
        if (retVal == null) {
            return;
        }
        Object respBody = retVal;
        if (retVal instanceof ResponseEntity) {
            ResponseEntity<?> respEntity = (ResponseEntity<?>) retVal;
            respBody = respEntity.getBody();
        }

        String details = getRequestDetails() + getResponseDetails(respBody);
        log.info("RESPONSE: " + details);
    }

    private String getRequestDetails() {
        String method = request.getMethod();
        String url = getRequestUrl();
        String clientIP = getClientIP();
        String usernameString = getUsername().map(uname -> "username: " + uname).orElse("");
        return method + " " + url + "; client IP: " + clientIP + "; " + usernameString;
    }

    private String getRequestUrl() {
        String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        return request.getRequestURL().toString() + queryString;
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private Optional<String> getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(username);
    }

    private Optional<Object> getRequestBodyOptional(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Parameter[] params  = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();
        return IntStream.range(0, params.length)
                .filter(i -> params[i].isAnnotationPresent(RequestBody.class))
                .mapToObj(i -> args[i])
                .findFirst();
    }

    private String getResponseDetails(Object responseBody) {
        String respDetails = "; status: " + getResponseHttpStatus();
        if (responseBody != null) {
            respDetails += "; response body: " + toJson(responseBody);
        }
        return respDetails;
    }

    private String getResponseHttpStatus() {
        int status = response.getStatus();
        return HttpStatus.valueOf(status).toString();
    }

    private boolean isMethodNonVoid(Signature signature) {
        MethodSignature methodSignature = (MethodSignature) signature;
        return !methodSignature.getReturnType().equals(Void.TYPE);
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert object to JSON string");
        }
    }
}
