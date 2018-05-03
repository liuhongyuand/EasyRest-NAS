package tech.dbgsoftware.easyrest.ioc.remote;

import com.google.gson.Gson;
import tech.dbgsoftware.easyrest.actors.remote.RemoteRequestUtil;
import tech.dbgsoftware.easyrest.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class EasyRestProxy implements InvocationHandler {

    private static final EasyRestProxy EASY_REST_PROXY = new EasyRestProxy();

    private EasyRestProxy(){}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Object result = RemoteRequestUtil.createRemoteRequest(method, args);
            if (method.getReturnType().getName().equalsIgnoreCase(Void.class.getSimpleName())) {
                return null;
            }
            return new Gson().fromJson(String.valueOf(result), method.getGenericReturnType());
        } catch (Exception e){
            LogUtils.error(String.format("Method %s invoke failed. Cause %s", method.getName(), e.getMessage()), e);
            return null;
        }
    }

    static EasyRestProxy getSingleInstance(){
        return EASY_REST_PROXY;
    }

}
