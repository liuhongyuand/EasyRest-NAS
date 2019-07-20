package tech.dbgsoftware.easyrest.model;

import io.netty.channel.ChannelHandlerContext;
import tech.dbgsoftware.easyrest.model.request.Request;
import tech.dbgsoftware.easyrest.model.request.RestObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpEntity {

    private Method method;
    private Request request;
    private Response response;
    private Class controller;
    private Boolean isOptionsCheck = false;
    private ChannelHandlerContext channelHandlerContext;
    private ResponseEntity responseEntity;
    private RestObject restObject;
    private Map<String, String> errorMap = new HashMap<>();
    private Object[] args = new Object[0];

    public HttpEntity(Request request, Response response, ChannelHandlerContext channelHandlerContext) {
        this.request = request;
        this.response = response;
        this.channelHandlerContext = channelHandlerContext;
    }

    public Boolean isOptionsCheck() {
        return isOptionsCheck;
    }

    public void setOptionsCheck(Boolean optionsCheck) {
        isOptionsCheck = optionsCheck;
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

    public Class getController() {
        return controller;
    }

    public void setController(Class controller) {
        this.controller = controller;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void addError(Exception e){
        errorMap.put(e.getMessage(), e.getClass().getSimpleName());
    }

    public void addError(Throwable e){
        errorMap.put(e.getMessage(), e.getClass().getSimpleName());
    }

    public RestObject getRestObject() {
        return restObject;
    }

    public void setRestObject(RestObject restObject) {
        this.restObject = restObject;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
