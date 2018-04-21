package com.easyrest.aop.pre;

import com.easyrest.aop.AopPreCommitStep;
import org.springframework.stereotype.Service;


/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class AopRequestValidateStep implements AopPreCommitStep {

    private static final String NOT_ALLOWED = "%s is not allowed.";

    @Override
    public <T>T executeStep(T entity) {
        return entity;
    }

}
