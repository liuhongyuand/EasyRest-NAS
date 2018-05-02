package tech.dbgsoftware.easyrest.annotations.parameter.validation;

import tech.dbgsoftware.easyrest.annotations.parameter.AllDefined;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.request.RestObject;

import java.util.ArrayList;
import java.util.List;

public class AllDefinedValidation {

    public static List<String> validate(HttpEntity httpEntity){
        List<String> errorList = new ArrayList<>();
        RestObject restObject = httpEntity.getRestObject();
        if (restObject.getMethod().isAnnotationPresent(AllDefined.class)){
            int[] index = new int[1];
            restObject.getParameterTypeMap().forEach((name, type) -> {
                if (httpEntity.getArgs()[index[0]] == null){
                    errorList.add(name);
                }
                index[0]++;
            });
        }
        return errorList;
    }

}
