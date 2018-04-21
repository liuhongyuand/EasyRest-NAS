package com.easyrest.aop;

import com.easyrest.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public interface AopStep {

    HttpEntity executeStep(HttpEntity httpEntity);

}
