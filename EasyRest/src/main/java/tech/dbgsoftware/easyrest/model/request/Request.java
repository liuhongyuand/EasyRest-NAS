package tech.dbgsoftware.easyrest.model.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import tech.dbgsoftware.easyrest.network.core.pipeline.utils.ByteBufUtils;
import tech.dbgsoftware.easyrest.network.router.UrlFormat;
import tech.dbgsoftware.easyrest.utils.LogUtils;

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

    private Map<String, String> parameterFromURL = new HashMap<>();

    public Request(FullHttpRequest fullHttpRequest) {
        try {
            requestHttpMethod = fullHttpRequest.method().name();
            requestUri = UrlFormat.getUrl(fullHttpRequest.uri(), parameterFromURL);
            fullHttpRequest.headers().forEach((header) -> headers.putIfAbsent(header.getKey(), header.getValue()));
            HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(fullHttpRequest);
            originByte = ByteBufUtils.readAll(fullHttpRequest.content());
            jsonData = new String(originByte, 0, originByte.length);
            if (postRequestDecoder.isMultipart()) {
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
        } finally {
            fullHttpRequest.release();
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

    public Map<String, String> getParameterFromURL() {
        return parameterFromURL;
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
