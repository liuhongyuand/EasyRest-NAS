package tech.dbgsoftware.easyrest.ioc.remote;

import tech.dbgsoftware.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import tech.dbgsoftware.easyrest.ioc.utils.BeanOperationUtils;

import java.lang.reflect.Proxy;

public class EasyRestServiceLookup {

    public static <T> T lookup(Class<T> service){
        if (EasyRestDistributedServiceBind.getLocalService().contains(service.getName())){
            return BeanOperationUtils.getBeansFromInterface(service);
        } else {
            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, EasyRestProxyFactory.getSingleProxyInstance());
        }
    }

    public static <T> T lookup(Class<T> service, long connectionMillis, long resultMillis){
        if (EasyRestDistributedServiceBind.getLocalService().contains(service.getName())){
            return BeanOperationUtils.getBeansFromInterface(service);
        } else {
            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, EasyRestProxyFactory.getProxyInstanceWithTimeout(connectionMillis, resultMillis));
        }
    }

    public static <T> T lookup(Class<T> service, String beanName){
        return lookup(service, beanName, 0, 0);
    }

    public static <T> T lookup(Class<T> service, String beanName, long connectionMillis, long resultMillis){
        if (EasyRestDistributedServiceBind.getLocalService().contains(service.getName())){
            return BeanOperationUtils.getBeansFromInterface(service, beanName);
        } else {
            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, EasyRestProxyFactory.getProxyInstanceWithTimeout(beanName, connectionMillis, resultMillis));
        }
    }

}
