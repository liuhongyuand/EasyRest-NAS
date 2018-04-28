package com.easyrest.ioc.remote;

import java.lang.reflect.Proxy;

public class EasyRestServiceLookup {

    public static <T> T lookup(Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, EasyRestProxy.getSingleInstance());
    }

}
