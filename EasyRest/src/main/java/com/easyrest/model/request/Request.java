package com.easyrest.model.request;

import com.easyrest.netty.core.pipeline.utils.ByteBufUtils;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private String requestMethod;

    private String requestUri;

    private Map<String, String> headers = new HashMap<>();

    private byte[] originByte;

    private String jsonData;

    public Request(FullHttpRequest fullHttpRequest) {
        requestMethod = fullHttpRequest.method().name();
        requestUri = fullHttpRequest.uri();
        fullHttpRequest.headers().forEach((header) -> headers.putIfAbsent(header.getKey(), header.getValue()));
        originByte = ByteBufUtils.readAll(fullHttpRequest.content());
        jsonData = new String(originByte, 0, originByte.length);
    }

    public byte[] getOriginByte() {
        return originByte;
    }

    public String getJsonData() {
        return jsonData;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
