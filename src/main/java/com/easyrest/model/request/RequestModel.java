package com.easyrest.model.request;


import com.easyrest.exception.ConditionMissingException;
import com.easyrest.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
public interface RequestModel {

    boolean notNullFieldCheck();

    boolean isAllFieldDefined();

    String getNotDefinedFields(Class aClass);

    void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException;

    String getFieldValue(String fieldName);

}
