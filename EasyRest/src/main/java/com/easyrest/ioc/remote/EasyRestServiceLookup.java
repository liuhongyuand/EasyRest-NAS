package com.easyrest.ioc.remote;

import com.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import com.easyrest.ioc.utils.BeanOperationUtils;

import java.lang.reflect.Proxy;

public class EasyRestServiceLookup {

    public static <T> T lookup(Class<T> service){
        if (EasyRestDistributedServiceBind.getLocalService().contains(service.getName())){
            return BeanOperationUtils.getBeansFromInterface(service);
        } else {
            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, EasyRestProxy.getSingleInstance());
        }
    }

}
