package com.dct.proxy.interceptor;

import com.dct.proxy.common.JsonUtils;
import com.dct.proxy.config.properties.ServerProxyProperties;
import com.dct.proxy.dto.request.ProxyAPIRequest;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProxyRequestInterceptor implements HandlerInterceptor {
    private final ServerProxyProperties proxyProperties;
    private final RestTemplate restTemplate;

    public ProxyRequestInterceptor(ServerProxyProperties proxyProperties, RestTemplate restTemplate) {
        this.proxyProperties = proxyProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        try {
            ProxyAPIRequest proxyAPIRequest = new ProxyAPIRequest();
            proxyAPIRequest.setUrl(proxyProperties.getPharmaIntegrationHost() + request.getRequestURI());
            proxyAPIRequest.setMethod(request.getMethod());
            Map<String, String> headers = Collections.list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(name -> name, request::getHeader));
            proxyAPIRequest.setHeaders(headers);

            Map<String, Object> params = request.getParameterMap()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().length == 1 ? e.getValue()[0] : Arrays.asList(e.getValue())
                    ));
            proxyAPIRequest.setParams(params);
            String body = new String(request.getInputStream().readAllBytes(), request.getCharacterEncoding());

            if (StringUtils.hasText(body)) {
                TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
                Map<String, Object> bodyMap = JsonUtils.parseJson(body, typeRef);
                proxyAPIRequest.setBody(bodyMap);
            } else {
                proxyAPIRequest.setBody(Collections.emptyMap());
            }

            HttpEntity<Object> entity = new HttpEntity<>(proxyAPIRequest);
            String url = proxyProperties.getHost() + proxyProperties.getProxyApi();
            // Call to proxy server API
            ResponseEntity<byte[]> serverResponse = restTemplate.postForEntity(url, entity, byte[].class);
            // Return original serverResponse from target API
            serverResponse.getHeaders().forEach((k, vList) -> vList.forEach(v -> response.addHeader(k, v)));
            response.setStatus(serverResponse.getStatusCode().value());
            response.getOutputStream().write(Optional.ofNullable(serverResponse.getBody()).orElse(new byte[0]));
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return false;
    }
}
