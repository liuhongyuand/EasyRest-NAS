package com.example.rest;

import com.example.InfoMation;
import tech.dbgsoftware.easyrest.annotations.method.*;
import tech.dbgsoftware.easyrest.annotations.parameter.AllDefined;
import tech.dbgsoftware.easyrest.aop.customer.CustomInjection;
import tech.dbgsoftware.easyrest.aop.customer.CustomResponse;
import tech.dbgsoftware.easyrest.exception.PermissionException;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import tech.dbgsoftware.easyrest.utils.JsonTranslationUtil;

@BindURL("/test")
public interface TestRestService extends CustomInjection, CustomResponse {

    @Get
    @SkipCustomerInject
    @SkipCustomerResponse
    void ping(String a);

    @Post
    @SkipCustomerInject
    @SkipCustomerResponse
    void postPing(String a);

    @Get
    @AllDefined
    @SkipCustomerInject
    @SkipCustomerResponse
    ResponseEntity testApi(String a, String b);

    @Get
    @AllDefined
    @SkipCustomerInject
    @SkipCustomerResponse
    String test(String a, String b);

    @Override
    default HttpEntity preCheck(HttpEntity httpEntity) {
        throw new PermissionException("No permission for this rest api.");
    }

    @Override
    default ResponseEntity processResponse(HttpEntity httpEntity, Object response) {
        return ResponseEntity.buildBaseResponse(InfoMation.encryptBasedDes(JsonTranslationUtil.toJsonString(response)));
    }

}
