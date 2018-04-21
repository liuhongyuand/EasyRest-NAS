package com.easyrest.model;

import com.easyrest.model.request.Request;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpEntity {

    private Method method;
    private Request request;
    private Response response;
    private ChannelHandlerContext channelHandlerContext;
    private ResponseEntity responseEntity;
    private Map<String, String> errorMap = new HashMap<>();

    public HttpEntity(Request request, Response response, ChannelHandlerContext channelHandlerContext) {
        this.request = request;
        this.response = response;
        this.channelHandlerContext = channelHandlerContext;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void addError(Exception e){
        errorMap.put(e.getMessage(), e.getClass().getSimpleName());
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
