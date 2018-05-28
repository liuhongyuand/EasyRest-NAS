package tech.dbgsoftware.easyrest.ioc.remote;

import tech.dbgsoftware.easyrest.ioc.remote.proxy.ProxyForBeanClassInvoke;
import tech.dbgsoftware.easyrest.ioc.remote.proxy.ProxyForBeanNameInvoke;

import java.lang.reflect.InvocationHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EasyRestProxyFactory{

    private static final InvocationHandler EASY_REST_SIMPLE_PROXY = new ProxyForBeanClassInvoke();

    private static final Map<String, InvocationHandler> INVOCATION_HANDLER_CACHE = new ConcurrentHashMap<>();

    private EasyRestProxyFactory(){}

    static InvocationHandler getSingleProxyInstance(){
        return EASY_REST_SIMPLE_PROXY;
    }

    static InvocationHandler getProxyInstanceWithTimeout(long connectionMillis, long resultMillis){
        if (connectionMillis <= 0 || resultMillis <= 0){
            connectionMillis = resultMillis = 0;
        }
        String key = keyResolver(ProxyForBeanClassInvoke.class.getSimpleName(), String.valueOf(connectionMillis), String.valueOf(resultMillis));
        if (!INVOCATION_HANDLER_CACHE.containsKey(key)){
            INVOCATION_HANDLER_CACHE.putIfAbsent(key, new ProxyForBeanClassInvoke(connectionMillis, resultMillis));
        }
        return INVOCATION_HANDLER_CACHE.get(key);
    }

    static InvocationHandler getProxyInstanceWithBeanName(String beanName){
        String key = keyResolver(ProxyForBeanNameInvoke.class.getSimpleName(), beanName);
        if (!INVOCATION_HANDLER_CACHE.containsKey(key)){
            INVOCATION_HANDLER_CACHE.putIfAbsent(key, new ProxyForBeanNameInvoke(beanName));
        }
        return INVOCATION_HANDLER_CACHE.get(key);
    }

    static InvocationHandler getProxyInstanceWithBeanNameAndTimeout(String beanName, long connectionMillis, long resultMillis){
        String key = keyResolver(ProxyForBeanNameInvoke.class.getSimpleName(), beanName, String.valueOf(connectionMillis), String.valueOf(resultMillis));
        if (!INVOCATION_HANDLER_CACHE.containsKey(key)){
            INVOCATION_HANDLER_CACHE.putIfAbsent(key, new ProxyForBeanNameInvoke(beanName, connectionMillis, resultMillis));
        }
        return INVOCATION_HANDLER_CACHE.get(key);
    }

    private static String keyResolver(String... args){
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg: args) {
            stringBuilder.append(arg).append("-");
        }
        return stringBuilder.toString();
    }

}
