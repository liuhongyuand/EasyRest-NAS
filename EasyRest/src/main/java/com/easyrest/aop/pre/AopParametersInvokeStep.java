package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.router.RouterProvider;
import com.easyrest.utils.ParameterTypeResolve;

public class AopParametersInvokeStep implements AopPreCommitStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        return invoke(entity);
    }

    private HttpEntity invoke(HttpEntity httpEntity){
        RestObject restObject = RouterProvider.getRestObjectMap().get(httpEntity.getRequest().getRequestUri());
        Object[] args = new Object[restObject.getParameterTypeMap().size()];
        if (httpEntity.getRequest().isMultipart()){
            final int[] index = {0};
            restObject.getParameterTypeMap().forEach((name, type) -> {
                try {
                    args[index[0]] = ParameterTypeResolve.resolveType(type, httpEntity.getRequest().getFormData().get(name));
                } catch (NumberFormatException e) {
                    httpEntity.addError(e);
                }
                index[0]++;
            });
            httpEntity.setArgs(args);
        }
        return httpEntity;
    }
}
