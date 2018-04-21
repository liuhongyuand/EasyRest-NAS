package com.easyrest.aop;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public interface AopStep {

    <T>T executeStep(T t);

}
