package com.easyrest.ioc.remote;

import akka.util.Timeout;
import com.easyrest.actors.remote.RemoteRequestUtil;
import com.google.gson.Gson;
import scala.concurrent.duration.Duration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class EasyRestProxy implements InvocationHandler {

    private static final EasyRestProxy EASY_REST_PROXY = new EasyRestProxy();

    private static final Timeout REQUEST_TIMEOUT = new Timeout(Duration.create(10, "seconds"));
    private EasyRestProxy(){}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = RemoteRequestUtil.getInvoke("example-service-2", "127.0.0.1", "2552", method.getName());
        String dataContext = new Gson().toJson(result);
        return new Gson().fromJson(dataContext, method.getGenericReturnType());
    }

    public static EasyRestProxy getSingleInstance(){
        return EASY_REST_PROXY;
    }

}
