package com.easyrest.aop.resolvers;

import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.network.router.RouterProvider;
import com.easyrest.utils.JsonTranslationUtil;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonDataResolve {

    public static Object[] resolveArgs(Type[] genericParameterTypes, Object[] args){
        for (int i = 0; i < args.length; i++) {
            args[i] = JsonTranslationUtil.fromJson(String.valueOf(args[i]), genericParameterTypes[i]);
        }
        return args;
    }

    public static Object[] resolveArgs(HttpEntity httpEntity){
        RestObject restObject = RouterProvider.getRestObject(httpEntity.getRequest().getRequestUri());
        Object[] args = new Object[restObject.getParameterTypeMap().size()];
        final int[] index = {0};
        if (args.length > 1){
            Map dataMap = JsonTranslationUtil.fromJson(httpEntity.getRequest().getJsonData(), Map.class);
            restObject.getParameterTypeMap().forEach((name, type) -> {
                if (httpEntity.getRestObject().getUriValues().containsKey(name)){
                    args[index[0]] = httpEntity.getRestObject().getUriValues().get(name);
                } else {
                    args[index[0]] = dataMap == null ? null : JsonTranslationUtil.fromJson(String.valueOf(dataMap.get(name)), type);
                }
                index[0]++;
            });
        } else if (args.length == 1){
            restObject.getParameterTypeMap().forEach((name, type) -> {
                if (httpEntity.getRestObject().getUriValues().containsKey(name)){
                    args[index[0]] = httpEntity.getRestObject().getUriValues().get(name);
                } else {
                    args[index[0]] = JsonTranslationUtil.fromJson(httpEntity.getRequest().getJsonData(), type);
                }
                index[0]++;
            });
        }
        return args;
    }

}
