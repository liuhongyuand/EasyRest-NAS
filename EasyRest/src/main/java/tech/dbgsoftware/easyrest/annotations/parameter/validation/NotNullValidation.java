package tech.dbgsoftware.easyrest.annotations.parameter.validation;

import tech.dbgsoftware.easyrest.annotations.parameter.NotNull;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.request.RestObject;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class NotNullValidation {

    public static List<String> validate(HttpEntity httpEntity){
        List<String> errorList = new ArrayList<>();
        RestObject restObject = httpEntity.getRestObject();
        for (Parameter parameter : restObject.getMethod().getParameters()) {
            if (parameter.isAnnotationPresent(NotNull.class)){
                int[] index = new int[1];
                restObject.getParameterTypeMap().forEach((name, type) -> {
                    if (httpEntity.getArgs()[index[0]] == null) {
                        errorList.add(name);
                    }
                    index[0]++;
                });
            }
        }
        return errorList;
    }
}
