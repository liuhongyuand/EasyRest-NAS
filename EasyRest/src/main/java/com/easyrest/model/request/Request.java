package com.easyrest.model.request;

import com.easyrest.netty.core.pipeline.utils.ByteBufUtils;
import com.easyrest.utils.LogUtils;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private String requestHttpMethod;

    private String requestUri;

    private Map<String, String> headers = new HashMap<>();

    private byte[] originByte;

    private String jsonData;

    private boolean isMultipart = false;

    private Map<String, String> formData = new HashMap<>();

    public Request(FullHttpRequest fullHttpRequest) {
        requestHttpMethod = fullHttpRequest.method().name();
        requestUri = fullHttpRequest.uri();
        fullHttpRequest.headers().forEach((header) -> headers.putIfAbsent(header.getKey(), header.getValue()));
        HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(fullHttpRequest);
        originByte = ByteBufUtils.readAll(fullHttpRequest.content());
        jsonData = new String(originByte, 0, originByte.length);
        if (postRequestDecoder.isMultipart()){
            postRequestDecoder.getBodyHttpDatas().forEach((interfaceHttpData -> {
                isMultipart = true;
                Attribute attribute = (Attribute) interfaceHttpData;
                try {
                    String key = interfaceHttpData.getName();
                    String value = attribute.getValue();
                    formData.put(key, value);
                } catch (IOException e) {
                    LogUtils.error(e.getMessage(), e);
                }
            }));
        }
    }

    public byte[] getOriginByte() {
        return originByte;
    }

    public String getJsonData() {
        return jsonData;
    }

    public String getRequestHttpMethod() {
        return requestHttpMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public boolean isMultipart() {
        return isMultipart;
    }
}
