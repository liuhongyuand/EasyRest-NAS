package tech.dbgsoftware.easyrest.aop.pre;

import tech.dbgsoftware.easyrest.aop.AopPreCommitStep;
import tech.dbgsoftware.easyrest.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/31.
 */
public class AopInitRequestInfoStep implements AopPreCommitStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        entity.setMethod(entity.getRestObject().getMethod());
        entity.setController(entity.getRestObject().getController());
        return entity;
    }

}
