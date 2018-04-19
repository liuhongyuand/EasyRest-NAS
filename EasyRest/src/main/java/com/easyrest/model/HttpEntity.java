package com.easyrest.model;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpEntity {

    private FullHttpRequest httpRequest;
    private FullHttpResponse httpResponse;

    public HttpEntity(FullHttpRequest httpRequest, FullHttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    public FullHttpRequest getHttpRequest() {
        return httpRequest;
    }

    public FullHttpResponse getHttpResponse() {
        return httpResponse;
    }
}
