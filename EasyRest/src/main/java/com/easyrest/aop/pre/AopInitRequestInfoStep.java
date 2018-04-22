package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.network.router.RouterProvider;

/**
 * Created by liuhongyu.louie on 2016/12/31.
 */
public class AopInitRequestInfoStep implements AopPreCommitStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        RestObject restObject = RouterProvider.getRestObjectMap().get(entity.getRequest().getRequestUri());
        entity.setMethod(restObject.getMethod());
        entity.setController(restObject.getController());
        return entity;
    }

}
