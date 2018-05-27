package tech.dbgsoftware.easyrest.ioc.remote;

import tech.dbgsoftware.easyrest.ioc.remote.proxy.ProxyForBeanClassInvoke;
import tech.dbgsoftware.easyrest.ioc.remote.proxy.ProxyForBeanNameInvoke;

public class EasyRestProxyFactory{

    private static final ProxyForBeanClassInvoke EASY_REST_PROXY = new ProxyForBeanClassInvoke();

    private EasyRestProxyFactory(){}

    static ProxyForBeanClassInvoke getSingleProxyInstance(){
        return EASY_REST_PROXY;
    }

    static ProxyForBeanNameInvoke getProxyInstance(String beanName){
        return new ProxyForBeanNameInvoke(beanName);
    }

}
