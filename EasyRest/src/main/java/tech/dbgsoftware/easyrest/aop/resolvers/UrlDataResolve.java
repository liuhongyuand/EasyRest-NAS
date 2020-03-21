package tech.dbgsoftware.easyrest.aop.resolvers;

import tech.dbgsoftware.easyrest.model.HttpEntity;

public class UrlDataResolve {

    public static Object[] resolveArgs(HttpEntity httpEntity){
        Object[] args = new Object[httpEntity.getRestObject().getParameterTypeMap().size()];
        final int[] index = {0};
        httpEntity.getRestObject().getParameterTypeMap().forEach((name, type) -> {
            try {
                if (httpEntity.getRestObject().getUriValues().containsKey(name)){
                    args[index[0]] = httpEntity.getRestObject().getUriValues().get(name);
                } else {
                    if (httpEntity.getRequest().getParameterFromURL().get(name) != null) {
                        args[index[0]] = ParameterTypeResolve.resolveType(type, httpEntity.getRequest().getParameterFromURL().get(name));
                    }
                }
            } catch (NumberFormatException e) {
                httpEntity.addError(e);
            }
            index[0]++;
        });
        return args;
    }
}
