package tech.dbgsoftware.easyrest.aop.customer;

import tech.dbgsoftware.easyrest.model.HttpEntity;

public interface CustomInjection {

    HttpEntity preCheck(HttpEntity httpEntity) throws Exception;

}
