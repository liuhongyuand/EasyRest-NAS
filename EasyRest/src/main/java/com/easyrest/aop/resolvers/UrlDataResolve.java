package com.easyrest.aop.resolvers;

import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.network.router.RouterProvider;

public class UrlDataResolve {

    public static Object[] resolveArgs(HttpEntity httpEntity){
        RestObject restObject = RouterProvider.getRestObjectMap().get(httpEntity.getRequest().getRequestUri());
        Object[] args = new Object[restObject.getParameterTypeMap().size()];
        final int[] index = {0};
        restObject.getParameterTypeMap().forEach((name, type) -> {
            try {
                args[index[0]] = ParameterTypeResolve.resolveType(type, httpEntity.getRequest().getParameterFromURL().get(name));
            } catch (NumberFormatException e) {
                httpEntity.addError(e);
            }
            index[0]++;
        });
        return args;
    }
}
