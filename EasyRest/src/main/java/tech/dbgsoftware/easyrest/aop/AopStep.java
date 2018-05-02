package tech.dbgsoftware.easyrest.aop;

import tech.dbgsoftware.easyrest.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public interface AopStep {

    HttpEntity executeStep(HttpEntity httpEntity) throws Exception;

}
