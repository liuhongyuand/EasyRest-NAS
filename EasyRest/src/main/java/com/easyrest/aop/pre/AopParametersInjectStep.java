package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.aop.resolvers.FormDataResolve;
import com.easyrest.aop.resolvers.JsonDataResolve;
import com.easyrest.aop.resolvers.UrlDataResolve;
import com.easyrest.model.HttpEntity;
import io.netty.handler.codec.http.HttpMethod;

public class AopParametersInjectStep implements AopPreCommitStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        return inject(entity);
    }

    private HttpEntity inject(HttpEntity httpEntity){
        if (httpEntity.getRequest().isMultipart()){
            httpEntity.setArgs(FormDataResolve.resolveArgs(httpEntity));
        } else {
            if (httpEntity.getRequest().getRequestHttpMethod().equalsIgnoreCase(HttpMethod.GET.name())){
                httpEntity.setArgs(UrlDataResolve.resolveArgs(httpEntity));
            } else {
                httpEntity.setArgs(JsonDataResolve.resolveArgs(httpEntity));
            }
        }
        return httpEntity;
    }
}
