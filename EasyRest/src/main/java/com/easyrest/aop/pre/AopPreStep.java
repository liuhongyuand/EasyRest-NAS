package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;

/**
 * Created by liuhongyu.louie on 2016/12/31.
 */
public class AopPreStep implements AopPreCommitStep {

    @Override
    public <T>T executeStep(T entity) {
        return entity;
    }

}
