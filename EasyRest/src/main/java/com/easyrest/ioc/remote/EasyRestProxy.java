package com.easyrest.ioc.remote;

import com.easyrest.actors.remote.RemoteRequestUtil;
import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class EasyRestProxy implements InvocationHandler {

    private static final EasyRestProxy EASY_REST_PROXY = new EasyRestProxy();

    private EasyRestProxy(){}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = RemoteRequestUtil.createRemoteRequest(method, args);
        if (method.getReturnType().getName().equalsIgnoreCase(Void.class.getSimpleName())){
            return null;
        }
        return new Gson().fromJson(String.valueOf(result), method.getGenericReturnType());
    }

    static EasyRestProxy getSingleInstance(){
        return EASY_REST_PROXY;
    }

}
