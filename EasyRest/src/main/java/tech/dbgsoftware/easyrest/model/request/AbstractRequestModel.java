package tech.dbgsoftware.easyrest.model.request;


import tech.dbgsoftware.easyrest.annotations.parameter.AllDefined;
import tech.dbgsoftware.easyrest.annotations.parameter.NotNull;
import tech.dbgsoftware.easyrest.annotations.parameter.Optional;
import tech.dbgsoftware.easyrest.exception.ConditionMissingException;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.utils.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by liuhongyu.louie on 2016/9/30.
 */
public abstract class AbstractRequestModel implements RequestModel {

    @Override
    public boolean notNullFieldCheck(){
        try {
            for (Field field : this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (field.isAnnotationPresent(NotNull.class) && (field.get(this) == null || StringUtils.isEmptyString((String) field.get(this)))) {
                    return false;
                }
            }
        } catch (IllegalAccessException ignored){}
        return true;
    }

    @Override
    public boolean isAllFieldDefined() {
        try {
            for (Field field : this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (field.get(this) == null || (field.getType().equals(String.class) && StringUtils.isEmptyString((String)field.get(this)))) {
                    if (!field.isAnnotationPresent(Optional.class)) {
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException ignored){}
        return true;
    }

    @Override
    public String getNotDefinedFields(Class aClass){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Field field : this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (aClass.equals(NotNull.class)){
                    if (field.get(this) == null || (field.getType().equals(String.class) && StringUtils.isEmptyString((String)field.get(this)))) {
                        stringBuilder.append(field.getName()).append(",").append(" ");
                    }
                } else if (aClass.equals(AllDefined.class)) {
                    if (field.get(this) == null || (field.getType().equals(String.class) && StringUtils.isEmptyString((String)field.get(this)))) {
                        stringBuilder.append(field.getName()).append(",").append(" ");
                    }
                }
            }
        } catch (IllegalAccessException ignored){}
        return stringBuilder.toString();
    }

    @Override
    public String getFieldValue(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return String.valueOf(field.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException {}

}
