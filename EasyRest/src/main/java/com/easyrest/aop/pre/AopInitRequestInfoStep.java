package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import com.easyrest.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/31.
 */
public class AopInitRequestInfoStep implements AopPreCommitStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        return entity;
    }

}
