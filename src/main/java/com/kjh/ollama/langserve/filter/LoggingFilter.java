package com.kjh.ollama.langserve.filter;

import com.kjh.ollama.langserve.filter.reqeustwrapper.CachedBodyHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;

/**
 * 로깅 필터
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 각 요청에 대해 고유한 traceId를 생성하여 MDC에 추가
        MDC.put("traceId", UUID.randomUUID().toString());
        try {
            doFilterWrapped(request, response, filterChain);
        } finally {
            // 필터 처리가 끝나면 MDC를 정리
            MDC.clear();
        }
    }

    /**
     * 요청과 응답을 래핑하여 로깅을 수행하는 메서드
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilterWrapped(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        CachedBodyHttpServletRequest requestWrapper = new CachedBodyHttpServletRequest(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            logRequest(requestWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    /**
     * Http 요청 정보를 로깅하는 메서드
     *
     * @param request
     * @throws IOException
     */
    private void logRequest(HttpServletRequest request) throws IOException {
        String queryString = request.getQueryString();
        String prefix = "Request";
        log.info("Request : {} uri=[{}] content-type=[{}]",
                request.getMethod(),
                queryString == null ? request.getRequestURI() : request.getRequestURI() + queryString,
                request.getContentType()
        );

        if (request.getContentType() != null) {
            if (isTextBasedContent(request.getContentType())) {
                logPayload("Request", request.getInputStream());
            } else {
                logPayload("Request", request.getParameterMap());
            }
        }

    }

    /**
     * HTTP 응답정보를 로깅하는 메서드
     * @param response
     * @throws IOException
     */
    private void logResponse(ContentCachingResponseWrapper  response) throws IOException {
        logPayload("Response", response.getContentInputStream());
    }

    /**
     * 입력 스트림의 내용을 로깅하는 메서드
     */
    private void logPayload(String prefix, InputStream inputStream) throws IOException {
        byte[] content = StreamUtils.copyToByteArray(inputStream);
        if (content.length > 0) {
            String contentString = new String(content);
            log.info("{} Payload: {}", prefix, contentString);
        }
    }
    /**
     * 파라미터 맵의 내용을 로깅하는 메서드
     */
    private void logPayload(String prefix, Map<String, String[]> parameter) {
        log.info("{} Payload: {}", prefix, getParameterString(parameter));
    }

    /**
     * 파라미터 맵을 문자열로 변환하는 메서드
     */
    private String getParameterString(Map<String, String[]> parameter) {
        return parameter.entrySet().stream()
                .map(e -> "[" + e.getKey() + " : " + String.join(", ", e.getValue()) + "]")
                .collect(Collectors.joining(", "));
    }


    /**
     * 컨텐츠 타입이 텍스트 기반인지 확인하는 메서드
     */
    private boolean isTextBasedContent(String contentType) {
        return contentType.startsWith(TEXT_PLAIN_VALUE) ||
                contentType.startsWith(APPLICATION_JSON_VALUE) ||
                contentType.startsWith(APPLICATION_XML_VALUE);
    }

}
