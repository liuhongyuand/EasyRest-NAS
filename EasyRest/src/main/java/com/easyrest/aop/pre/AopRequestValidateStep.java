package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.exception.MethodNotAllowedException;
import com.easyrest.exception.PageNotFoundException;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.router.RouterProvider;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.springframework.stereotype.Service;


/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class AopRequestValidateStep implements AopPreCommitStep {

    private static final String NOT_ALLOWED = "%s is not allowed.";

    private static final String NOT_FOUND = "%s is not found.";

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        String url = entity.getRequest().getRequestUri();
        RestObject restObject = RouterProvider.getRestObjectMap().get(url);
        if (restObject == null){
            entity.addError(new PageNotFoundException(String.format(NOT_FOUND, url)));
            entity.getResponse().getRealResponse().setStatus(HttpResponseStatus.NOT_FOUND);
        } else {
            if (!restObject.getMethod().getName().equalsIgnoreCase(entity.getRequest().getRequestMethod())){
                entity.addError(new MethodNotAllowedException(String.format(NOT_ALLOWED, entity.getRequest().getRequestMethod())));
                entity.getResponse().getRealResponse().setStatus(HttpResponseStatus.METHOD_NOT_ALLOWED);
            }
        }
        return entity;
    }

}
