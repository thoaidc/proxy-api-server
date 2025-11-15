package com.dct.proxy.interceptor;

import com.dct.proxy.common.JsonUtils;
import com.dct.proxy.config.properties.ServerProxyProperties;
import com.dct.proxy.dto.request.ProxyAPIRequest;
import com.dct.proxy.dto.response.BaseResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProxyRequestInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ProxyRequestInterceptor.class);
    private final ServerProxyProperties proxyProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ProxyRequestInterceptor(ServerProxyProperties proxyProperties,
                                   RestTemplate restTemplate,
                                   ObjectMapper objectMapper) {
        this.proxyProperties = proxyProperties;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws IOException {
        try {
            ProxyAPIRequest proxyAPIRequest = new ProxyAPIRequest();
            proxyAPIRequest.setMethod(request.getMethod());

            // Build proxy API
            String url = UriComponentsBuilder.fromUriString(proxyProperties.getPharmaIntegrationHost())
                    .path(request.getRequestURI())
                    .query(request.getQueryString())
                    .toUriString();
            proxyAPIRequest.setUrl(url);
            log.info("[PROXY_API_INTERCEPTOR] - Incoming request: method={}, url={}", request.getMethod(), url);

            // Copy and wrapped original request headers
            Map<String, String> headers = Collections.list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(name -> name, request::getHeader));
            proxyAPIRequest.setHeaders(headers);

            String body = new String(request.getInputStream().readAllBytes(), request.getCharacterEncoding());
            log.debug("[PROXY_API_INTERCEPTOR] - Request headers: {}", headers);

            // Copy and wrapped original body
            if (StringUtils.hasText(body)) {
                TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
                Map<String, Object> bodyMap = JsonUtils.parseJson(body, typeRef);
                proxyAPIRequest.setBody(bodyMap);
            } else {
                proxyAPIRequest.setBody(Collections.emptyMap());
            }

            log.debug("[PROXY_API_INTERCEPTOR] - Request original body: {}", proxyAPIRequest.getBody());
            // Call to proxy server
            HttpEntity<Object> entity = new HttpEntity<>(proxyAPIRequest);
            String serverProxyUrl = proxyProperties.getHost() + proxyProperties.getProxyApi();
            log.info("[PROXY_API_INTERCEPTOR] - Forwarding to proxy URL: {}", serverProxyUrl);
            ResponseEntity<byte[]> serverResponse = restTemplate.postForEntity(serverProxyUrl, entity, byte[].class);
            // Return original serverResponse from target API
            copyHeaders(response, serverResponse.getHeaders());
            response.setStatus(serverResponse.getStatusCode().value());
            response.getOutputStream().write(Optional.ofNullable(serverResponse.getBody()).orElse(new byte[0]));
            log.info("[PROXY_API_INTERCEPTOR] - Received response: status={}", serverResponse.getStatusCode());
        } catch (Exception e) {
            log.error("[PROXY_API_INTERCEPTOR] - Forward failed: {}", e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            BaseResponseDTO responseDTO = BaseResponseDTO.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .success(Boolean.FALSE)
                    .build();
            byte[] jsonBytes = objectMapper.writeValueAsBytes(responseDTO);
            ServletOutputStream out = response.getOutputStream();
            out.write(jsonBytes);
            out.flush();
            out.close();
        }

        return false;
    }

    private void copyHeaders(HttpServletResponse target, HttpHeaders source) {
        source.forEach((name, values) -> values.forEach(v -> target.addHeader(name, v)));
    }
}
