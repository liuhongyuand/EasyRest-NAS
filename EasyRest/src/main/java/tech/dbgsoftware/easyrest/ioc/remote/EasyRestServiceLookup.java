package tech.dbgsoftware.easyrest.ioc.remote;

import tech.dbgsoftware.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import tech.dbgsoftware.easyrest.ioc.utils.BeanOperationUtils;

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
