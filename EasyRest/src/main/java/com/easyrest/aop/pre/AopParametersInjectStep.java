package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.aop.resolvers.FormDataResolve;
import com.easyrest.aop.resolvers.JsonDataResolve;
import com.easyrest.model.HttpEntity;

public class AopParametersInjectStep implements AopPreCommitStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        return inject(entity);
    }

    private HttpEntity inject(HttpEntity httpEntity){
        if (httpEntity.getRequest().isMultipart()){
            httpEntity.setArgs(FormDataResolve.resolveArgs(httpEntity));
        } else {
            httpEntity.setArgs(JsonDataResolve.resolveArgs(httpEntity));
        }
        return httpEntity;
    }
}
