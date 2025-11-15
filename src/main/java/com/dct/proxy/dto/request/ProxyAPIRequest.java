package com.dct.proxy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class ProxyAPIRequest {
    @NotBlank(message = "URL could not be empty")
    private String url;

    @NotBlank(message = "Method could not be empty")
    @Pattern(regexp = "^(GET|POST|PUT|PATCH|DELETE)$", message = "Invalid method. Please uses GET, POST, PUT, PATCH or DELETE")
    private String method;

    private Map<String, String> headers;
    private Map<String, Object> params;
    private Map<String, Object> body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return Optional.ofNullable(headers).orElse(new HashMap<>());
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParams() {
        return Optional.ofNullable(params).orElse(new HashMap<>());
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getBody() {
        return Optional.ofNullable(body).orElse(new HashMap<>());
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
