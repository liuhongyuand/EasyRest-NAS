package com.easyrest.aop.resolvers;

import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.network.router.RouterProvider;
import com.google.gson.Gson;

import java.util.Map;

public class JsonDataResolve {

    private static final Gson GSON = new Gson();

    public static Object[] resolveArgs(HttpEntity httpEntity){
        RestObject restObject = RouterProvider.getRestObject(httpEntity.getRequest().getRequestUri());
        Object[] args = new Object[restObject.getParameterTypeMap().size()];
        final int[] index = {0};
        if (args.length > 1){
            Map dataMap = GSON.fromJson(httpEntity.getRequest().getJsonData(), Map.class);
            restObject.getParameterTypeMap().forEach((name, type) -> {
                if (httpEntity.getRestObject().getUriValues().containsKey(name)){
                    args[index[0]] = httpEntity.getRestObject().getUriValues().get(name);
                } else {
                    args[index[0]] = GSON.fromJson(String.valueOf(dataMap.get(name)), type);
                }
                index[0]++;
            });
        } else if (args.length == 1){
            restObject.getParameterTypeMap().forEach((name, type) -> {
                if (httpEntity.getRestObject().getUriValues().containsKey(name)){
                    args[index[0]] = httpEntity.getRestObject().getUriValues().get(name);
                } else {
                    args[index[0]] = GSON.fromJson(httpEntity.getRequest().getJsonData(), type);
                }
                index[0]++;
            });
        }
        return args;
    }

}
