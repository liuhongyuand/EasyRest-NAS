package tech.dbgsoftware.easyrest.aop.customer;

import tech.dbgsoftware.easyrest.model.HttpEntity;

public interface CustomResponse {

    Object processResponse(HttpEntity httpEntity, Object response);

}
