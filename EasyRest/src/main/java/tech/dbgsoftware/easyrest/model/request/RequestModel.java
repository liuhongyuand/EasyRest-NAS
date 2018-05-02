package tech.dbgsoftware.easyrest.model.request;


import tech.dbgsoftware.easyrest.exception.ConditionMissingException;
import tech.dbgsoftware.easyrest.model.HttpEntity;

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
