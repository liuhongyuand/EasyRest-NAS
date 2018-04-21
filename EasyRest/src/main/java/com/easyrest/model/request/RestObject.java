package com.easyrest.model.request;

import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RestObject {

    private Method method;

    private List<HttpMethod> httpMethodList = new ArrayList<>();

    private Class controller;

    public RestObject(Method method, HttpMethod httpMethodName, Class controller) {
        this.method = method;
        this.httpMethodList.add(httpMethodName);
        this.controller = controller;
    }

    public void addHttpMethod(HttpMethod httpMethod){
        if (!httpMethodList.contains(httpMethod)){
            httpMethodList.add(httpMethod);
        }
    }

    public Method getMethod() {
        return method;
    }

    public List<HttpMethod> getHttpMethodList() {
        return httpMethodList;
    }

    public Class getController() {
        return controller;
    }

    @Override
    public int hashCode() {
        return method.hashCode() + controller.hashCode();
    }
}
