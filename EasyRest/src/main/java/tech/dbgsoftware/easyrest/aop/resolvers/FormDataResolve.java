package tech.dbgsoftware.easyrest.aop.resolvers;

import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.request.RestObject;
import tech.dbgsoftware.easyrest.network.router.RouterProvider;

public class FormDataResolve {

    public static Object[] resolveArgs(HttpEntity httpEntity){
        RestObject restObject = RouterProvider.getRestObject(httpEntity.getRequest().getRequestUri());
        Object[] args = new Object[restObject.getParameterTypeMap().size()];
        final int[] index = {0};
        restObject.getParameterTypeMap().forEach((name, type) -> {
            try {
                if (httpEntity.getRestObject().getUriValues().containsKey(name)){
                    args[index[0]] = httpEntity.getRestObject().getUriValues().get(name);
                } else {
                    args[index[0]] = ParameterTypeResolve.resolveType(type, httpEntity.getRequest().getFormData().get(name));
                }
            } catch (NumberFormatException e) {
                httpEntity.addError(e);
            }
            index[0]++;
        });
        return args;
    }

}
