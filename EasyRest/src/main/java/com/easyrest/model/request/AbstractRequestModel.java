package com.easyrest.model.request;


import com.easyrest.EasyRest;
import com.easyrest.annotations.parameter.AllDefined;
import com.easyrest.annotations.parameter.NotNull;
import com.easyrest.annotations.parameter.Optional;
import com.easyrest.exception.ConditionMissingException;
import com.easyrest.model.HttpEntity;
import com.easyrest.utils.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by liuhongyu.louie on 2016/9/30.
 */
public abstract class AbstractRequestModel implements RequestModel {

    public void initRequestModel(){
        EasyRest.register(this);
    }

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
