package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.exception.MethodNotAllowedException;
import com.easyrest.exception.PageNotFoundException;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.network.router.RouterProvider;
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
    public HttpEntity executeStep(HttpEntity entity) throws Exception {
        String url = entity.getRequest().getRequestUri();
        RestObject restObject = RouterProvider.getRestObjectMap().get(url);
        if (restObject == null){
            entity.getResponse().getRealResponse().setStatus(HttpResponseStatus.NOT_FOUND);
            throw new PageNotFoundException(String.format(NOT_FOUND, url));
        } else {
            if (!restObject.getHttpMethodList().contains(entity.getRequest().getRequestHttpMethod().toLowerCase())){
                entity.getResponse().getRealResponse().setStatus(HttpResponseStatus.METHOD_NOT_ALLOWED);
                throw new MethodNotAllowedException(String.format(NOT_ALLOWED, entity.getRequest().getRequestHttpMethod()));
            }
        }
        return entity;
    }

}
